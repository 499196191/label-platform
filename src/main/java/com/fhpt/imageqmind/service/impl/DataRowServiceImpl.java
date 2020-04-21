package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.constant.RowStatus;

import com.fhpt.imageqmind.constant.TaskStatus;
import com.fhpt.imageqmind.domain.*;


import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.DataRowVo;

import com.fhpt.imageqmind.objects.vo.DataSetDetail;
import com.fhpt.imageqmind.objects.vo.LabelResultCount;
import com.fhpt.imageqmind.objects.vo.TagLabelVo;

import com.fhpt.imageqmind.repository.*;


import com.fhpt.imageqmind.service.DataRowService;

import com.fhpt.imageqmind.utils.ObjectConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据行业务实现
 * @author Marty
 */
@Slf4j
@Service
public class DataRowServiceImpl implements DataRowService {

    @Autowired
    private DataRowRepository dataRowRepository;
    @Autowired
    private DataSetRepository dataSetRepository;
    @Autowired
    private TaskInfoRepository taskInfoRepository;
    @Autowired
    private LabelResultRepository labelResultRepository;
    @Autowired
    private TaskRowMapRepository taskRowMapRepository;
    @Autowired
    private ClassifyLabelResultRepository classifyLabelResultRepository;

    @Override
    public DataRowVo detail(Long dataRowId, Long taskId) {
        DataRowEntity dataRowEntity = dataRowRepository.getOne(dataRowId, taskId);
        TaskInfoEntity taskInfoEntity = taskInfoRepository.getOne(taskId);
        if (dataRowEntity == null || taskInfoEntity == null) {
            return null;
        }
        DataRowVo dataRowVo = new DataRowVo();
        BeanUtils.copyProperties(dataRowEntity, dataRowVo);
        dataRowVo.setAllSize(taskInfoEntity.getSize());
        dataRowVo.setFinishedSize(taskInfoEntity.getFinishedSize());
        dataRowVo.setProcess(taskInfoEntity.getProcess().doubleValue());
        //处理标签信息
        List<TagLabelVo> tagLabelVos = new ArrayList<>();
        taskInfoEntity.getTagLabelEntities().forEach(tagLabelEntity -> {
            TagLabelVo tagLabelVo = new TagLabelVo();
            BeanUtils.copyProperties(tagLabelEntity, tagLabelVo);
            //查询不同标签下的标注量大小
            tagLabelVo.setTagedsize(labelResultRepository.getLabelResultSize(tagLabelEntity.getId(), taskId, dataRowId));
            dataRowVo.setTagSize(dataRowVo.getTagSize() + tagLabelVo.getTagedsize());
            //标签下的所有标注
            List<LabelResultEntity> labelResultEntityList = labelResultRepository.getAll(taskId, tagLabelEntity.getId(), dataRowId);
            List<LabelResultCount> countList = new ArrayList<>();
            ObjectConvertUtil.labelResultConvertToLabelCount(labelResultEntityList, countList);
            tagLabelVo.setLabelResultCount(countList);
            tagLabelVos.add(tagLabelVo);
        });
        dataRowVo.setTagLabels(tagLabelVos);

        return dataRowVo;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void batchInsert(List<String> contents, Long id) {
        Optional<DataSetEntity> dataSetEntityOptional = dataSetRepository.findById(id);
        if (!dataSetEntityOptional.isPresent()) {
            return;
        }
        DataSetEntity dataSetEntity = dataSetEntityOptional.get();
        List<DataRowEntity> rows = contents.stream().map(content -> {
            DataRowEntity dataRowEntity = new DataRowEntity();
            dataRowEntity.setContent(content);
            dataRowEntity.setDataSet(dataSetEntity);
            dataRowEntity.setRow(contents.indexOf(content));
            Timestamp now = Timestamp.from(Instant.now());
            dataRowEntity.setCreateTime(now);
            dataRowEntity.setUpdateTime(now);
            return dataRowEntity;
        }).collect(Collectors.toList());
        dataRowRepository.saveAll(rows);
    }

    @Override
    public DataSetDetail query(Long dataSetId, int page, int pageSize) {
        DataSetDetail dataSetDetail = new DataSetDetail();
        Page<DataRowEntity> dataRows = dataRowRepository.getDataRows(dataSetId, PageRequest.of(page - 1, pageSize));
        Optional<DataSetEntity> optionalDataSetEntity = dataSetRepository.findById(dataSetId);
        if (!optionalDataSetEntity.isPresent()) {
            log.error("数据集ID{}不存在", dataSetId);
            return dataSetDetail;
        }
        DataSetEntity dataSetEntity = optionalDataSetEntity.get();
        dataSetDetail.setId(dataSetId);
        dataSetDetail.setName(dataSetEntity.getName());
        dataSetDetail.setColumnName(dataSetEntity.getColumnName());
        PageInfo<DataSetDetail.DataRowDetail> result = new PageInfo();
        result.setTotal(dataRows.getTotalElements());
        List<DataSetDetail.DataRowDetail> list = new ArrayList<>();
        dataRows.get().forEach(dataRowEntity -> {
            DataSetDetail.DataRowDetail dataRowDetail = new DataSetDetail.DataRowDetail();
            dataRowDetail.setId(dataRowEntity.getId());
            dataRowDetail.setContent(dataRowEntity.getContent());
            //寻找实体标签
            List<LabelResultEntity> labelResultEntities = labelResultRepository.getAllByRowId(dataRowEntity.getId());
            String entityTags = "";
            if (labelResultEntities != null && labelResultEntities.size() != 0) {
                entityTags = labelResultEntities.stream().map(LabelResultEntity::getTagLabel).distinct().map(TagLabelEntity::getName).collect(Collectors.joining(","));
                dataSetDetail.setTagged(true);
            }
            dataRowDetail.setEntityTags(entityTags);
            //寻找分类标签
            List<ClassifyLabelResultEntity> classifyLabelResultEntities = classifyLabelResultRepository.getAllByRowId(dataRowEntity.getId());
            String classifyTags = "";
            if (classifyLabelResultEntities != null && classifyLabelResultEntities.size() != 0) {
                classifyTags = classifyLabelResultEntities.stream().map(ClassifyLabelResultEntity::getTagLabel).distinct().map(TagLabelEntity::getName).collect(Collectors.joining(","));
                dataSetDetail.setTagged(true);
            }
            dataRowDetail.setClassifyTags(classifyTags);
            list.add(dataRowDetail);
        });
        result.setList(list);
        dataSetDetail.setDataRowDetails(result);
        return dataSetDetail;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public long updateDataRowMapStatus(Long dataRowId, Long taskId, RowStatus rowStatus) {
        TaskRowMapEntity taskRowMapEntity = taskRowMapRepository.getOne(dataRowId, taskId);
        //标记为已完成时，需要更新标记任务的状态信息
        if (taskRowMapEntity.getStatus() != RowStatus.LABELED.getType() && RowStatus.LABELED == rowStatus) {
            Optional<TaskInfoEntity> taskInfoOptional = taskInfoRepository.findById(taskId);
            Assert.isTrue(taskInfoOptional.isPresent(), "taskId信息为空！");
            TaskInfoEntity taskInfoEntity = taskInfoOptional.get();
            int finishedSize = taskInfoEntity.getStatus() != TaskStatus.FINISHED.getType() ? taskInfoEntity.getFinishedSize() + 1 : taskInfoEntity.getFinishedSize();
            BigDecimal finishedDecimal = new BigDecimal(finishedSize);
            BigDecimal sizeDecimal = new BigDecimal(taskInfoEntity.getSize());
            //最后一批标记更新状态为已完成
            if (finishedSize == taskInfoEntity.getSize()) {
                taskInfoEntity.setStatus(TaskStatus.FINISHED.getType());
                taskInfoEntity.setProcess(new BigDecimal(1));
            } else {
                taskInfoEntity.setStatus(TaskStatus.STARTING.getType());
                taskInfoEntity.setProcess(finishedDecimal.divide(sizeDecimal, 4, BigDecimal.ROUND_HALF_UP));
            }
            taskInfoEntity.setFinishedSize(finishedSize);
        }
        taskRowMapEntity.setStatus(rowStatus.getType());
        taskRowMapEntity.setCreateBy("");
        taskRowMapRepository.save(taskRowMapEntity);
        return getNext(taskId);
    }

    public long getNext(Long taskId){
        //寻找用户进入标注文章的文章id（-1代表没有下一篇文章）
        List<TaskRowMapEntity> next = taskRowMapRepository.getNext(taskId, PageRequest.of(0, 1));
        return next.size() != 0 ? next.get(0).getRowId() : -1;
    }
}
