package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.constant.TaskStatus;

import com.fhpt.imageqmind.domain.DataRowEntity;
import com.fhpt.imageqmind.domain.LabelResultEntity;
import com.fhpt.imageqmind.domain.TagLabelEntity;
import com.fhpt.imageqmind.domain.TaskInfoEntity;
import com.fhpt.imageqmind.objects.vo.*;
import com.fhpt.imageqmind.repository.DataRowRepository;
import com.fhpt.imageqmind.repository.LabelResultRepository;
import com.fhpt.imageqmind.repository.TagLabelRepository;
import com.fhpt.imageqmind.repository.TaskInfoRepository;
import com.fhpt.imageqmind.service.LabelResultService;

import com.fhpt.imageqmind.service.LoginService;


import com.fhpt.imageqmind.service.TaskInfoService;
import com.google.common.collect.Sets;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标签标注业务实现类
 * @author Marty
 */
@Service
public class LabelResultServiceImpl implements LabelResultService {

    @Autowired
    private LabelResultRepository labelResultRepository;
    @Autowired
    private DataRowRepository dataRowRepository;
    @Autowired
    private TaskInfoRepository taskInfoRepository;
    @Autowired
    private TagLabelRepository tagLabelRepository;
    @Autowired
    private TaskInfoService taskInfoService;
    @Autowired
    private LoginService loginService;
    /**
     * 标注匹配
     */
    private static final Pattern ANNOTATION_PATTERN = Pattern.compile("</?span(\\s+anno-id=|>)");
    /**
     * 标注匹配
     */
    private static final String ANNOTATION_REGEX = "</?span[^>]*>";

    /**
     * 查询列表
     */
    @Override
    public LabelResultDetail query(long dataRowId, long taskId) {
        LabelResultDetail labelResultDetail = new LabelResultDetail();
        List<LabelResultVo> list = new ArrayList<>();
        List<LabelResultEntity> result = labelResultRepository.query(dataRowId, taskId);
        convertLabelResultInfo(result, list);
        labelResultDetail.setList(list);
        labelResultDetail.setTaskInfo(taskInfoService.detail(taskId));
        return labelResultDetail;
    }

    /**
     * 新增
     *
     * @param labelIndexVos
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean insert(List<LabelIndexVo> labelIndexVos) {
        List<LabelResultEntity> list = new ArrayList<>();
        Optional<TaskInfoEntity> taskInfoOptional = taskInfoRepository.findById(labelIndexVos.get(0).getTaskId());
        if (!taskInfoOptional.isPresent()) {
            return false;
        }
        TaskInfoEntity taskInfoEntity = taskInfoOptional.get();
        labelIndexVos.forEach(labelIndexVo -> {
            LabelResultEntity labelResult = new LabelResultEntity();
            labelResult.setStart(labelIndexVo.getStart());
            labelResult.setEnd(labelIndexVo.getEnd());
            labelResult.setColorIndex(labelIndexVo.getColorIndex());
            labelResult.setContent(labelIndexVo.getContent());
            Optional<DataRowEntity> dataRowOptional = dataRowRepository.findById(labelIndexVo.getDataRowId());
            Assert.isTrue(dataRowOptional.isPresent(), "dataRowId信息为空！");
            labelResult.setDataRow(dataRowOptional.get());
            labelResult.setTaskInfo(taskInfoEntity);
            Optional<TagLabelEntity> tagLabelOptional = tagLabelRepository.findById(labelIndexVo.getTagId());
            Assert.isTrue(tagLabelOptional.isPresent(), "tagId信息为空！");
            labelResult.setTagLabel(tagLabelOptional.get());
            Timestamp now = Timestamp.from(Instant.now());
            labelResult.setCreateTime(now);
            labelResult.setUpdateTime(now);
            labelResult.setCreatedBy(loginService.getLoginUserName());
            list.add(labelResult);
        });
        labelResultRepository.saveAll(list);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(List<Long> ids) {
        try {
            ids.forEach(id -> labelResultRepository.deleteById(id));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 是否存在交叉或嵌套
     * @param labelIndexVos
     * @return
     */
    @Override
    public boolean isCrossAndHandelDuplicate(List<LabelIndexVo> labelIndexVos, List<LabelIndexVo> handelResult) {
        Long dataRowId = labelIndexVos.get(0).getDataRowId();
        Long taskId = labelIndexVos.get(0).getTaskId();
        List<LabelResultEntity> list = labelResultRepository.query(dataRowId, taskId);
        for (LabelIndexVo labelIndexVo : labelIndexVos) {
            boolean isDuplicate = false;
            for (LabelResultEntity labelResultEntity : list) {
                //可能重复，此处放行，并去重
                if (labelIndexVo.getStart().equals(labelResultEntity.getStart()) && labelIndexVo.getEnd().equals(labelResultEntity.getEnd())) {
                    isDuplicate = true;
                    break;
                } else if (labelIndexVo.getStart() >= labelResultEntity.getStart() && labelIndexVo.getStart() <= labelResultEntity.getEnd()) {
                    return true;
                } else if (labelIndexVo.getEnd() >= labelResultEntity.getStart() && labelIndexVo.getEnd() <= labelResultEntity.getEnd()) {
                    return true;
                }
            }
            if (!isDuplicate) {
                handelResult.add(labelIndexVo);
            }
        }
        return false;
    }

    @Override
    public List<LabelResultEntity> parseLabelResult(long taskId, long rowId, String annotatedDoc) {
        annotatedDoc = annotatedDoc.replaceAll("<br/?>", "\n");
        Optional<TaskInfoEntity> taskInfoOptional = taskInfoRepository.findById(taskId);
        Assert.isTrue(taskInfoOptional.isPresent(), "taskId信息为空！");
        Optional<DataRowEntity> dataRowOptional = dataRowRepository.findById(rowId);
        Assert.isTrue(dataRowOptional.isPresent(), "dataRowId信息为空！");

        int annoStartSpanStart = 0;
        List<LabelResultEntity> labelResultEntityList = new ArrayList<>();
        while ((annoStartSpanStart = annotatedDoc.indexOf("<span anno-id=", annoStartSpanStart)) >= 0) {
            int annoStartSpanEnd = annotatedDoc.indexOf(">", annoStartSpanStart);
            String annoStartSpan = annotatedDoc.substring(annoStartSpanStart, annoStartSpanEnd);
            //这里是标签ID
            String annotationId = findFirstMatch(annoStartSpan, Pattern.compile("anno-id=\"(\\d+)\""), 1);
            int annoEndSpanStart = searchAnnotationEnd(annotatedDoc, annoStartSpanStart);
            int realStart = getRealStart(annotatedDoc, annoStartSpanStart);
            annoStartSpanStart = annoStartSpanEnd + 1;
            if (StringUtils.isEmpty(annotationId)) {
                continue;
            }
            String annoContent = annotatedDoc.substring(annoStartSpanEnd + 1, annoEndSpanStart).replaceAll(ANNOTATION_REGEX, "");
            if (annoContent.isEmpty()) {
                continue;
            }
            int realEnd = realStart + annoContent.length();
            LabelResultEntity labelResultEntity = new LabelResultEntity();
            Optional<TagLabelEntity> tagLabelOptional = tagLabelRepository.findById(Long.parseLong(annotationId));
            Assert.isTrue(tagLabelOptional.isPresent(), "tagId信息为空！");
            labelResultEntity.setTagLabel(tagLabelOptional.get());
            labelResultEntity.setTaskInfo(taskInfoOptional.get());
            labelResultEntity.setDataRow(dataRowOptional.get());
            labelResultEntity.setStart(realStart);
            labelResultEntity.setEnd(realEnd);
            Timestamp now = Timestamp.from(Instant.now());
            labelResultEntity.setCreateTime(now);
            labelResultEntity.setUpdateTime(now);
            labelResultEntity.setCreatedBy(loginService.getLoginUserName());
            labelResultEntityList.add(labelResultEntity);
        }
        return labelResultEntityList;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void initLabelResult(List<LabelResultEntity> list, long taskId, long rowId) {
        //清空数据
//        labelResultRepository.deleteAllCondition(rowId, taskId);
        List<LabelResultEntity> deleteList = labelResultRepository.query(rowId, taskId);
        labelResultRepository.deleteAll(deleteList);
        //批量添加
        labelResultRepository.saveAll(list);
    }

    private String findFirstMatch(String text, Pattern pattern, int group) {
        if (text != null && pattern != null) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                int groupCnt = matcher.groupCount();
                if (groupCnt >= group) {
                    return matcher.group(group);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    /**
     * 检索与标注起始对应的结束位置
     *
     * @param annotationContent 已标注文档
     * @param searchStart       检索起始
     * @return 标注结束位置
     */
    private int searchAnnotationEnd(String annotationContent, int searchStart) {
        // 起始标注设定为1，使用</span>进行消解，消解为0时，对应的就是相应的结束位置
        int annotated = 0;
        Matcher annotationMatcher = ANNOTATION_PATTERN.matcher(annotationContent.substring(searchStart));
        while (annotationMatcher.find()) {
            String annoContent = annotationMatcher.group();
            boolean isAnnoEnd = annoContent.startsWith("</");
            if (isAnnoEnd) {
                annotated--;
            } else {
                annotated++;
            }
            if (annotated == 0) {
                return searchStart + annotationMatcher.start();
            }
        }
        return -1;
    }

    /**
     * 获取标注的真正位置
     *
     * @param annotatedContent 已标注内容
     * @param annotationStart  标注位置
     * @return 真正位置
     */
    private int getRealStart(String annotatedContent, int annotationStart) {
        return annotatedContent.substring(0, annotationStart).replaceAll("</?span[^>]*>", "").length();
    }

    private void convertLabelResultInfo(List<LabelResultEntity> source, List<LabelResultVo> target){
        source.forEach(labelResultEntity -> {
            LabelResultVo labelResultVo = new LabelResultVo();
            labelResultVo.setTagLabel(new TagLabelVo());
            //            labelResultVo.setDataRow(new DataRowVo());
            //            labelResultVo.setTaskInfo(new TaskInfoVo());
            //需要的属性直接拷贝，考虑日期不需要就暂不处理
            BeanUtils.copyProperties(labelResultEntity, labelResultVo);
            //            BeanUtils.copyProperties(labelResultEntity.getDataRow(), labelResultVo.getDataRow());
            BeanUtils.copyProperties(labelResultEntity.getTagLabel(), labelResultVo.getTagLabel());
            //            BeanUtils.copyProperties(labelResultEntity.getTaskInfo(), labelResultVo.getTaskInfo());
            target.add(labelResultVo);
        });
    }
}
