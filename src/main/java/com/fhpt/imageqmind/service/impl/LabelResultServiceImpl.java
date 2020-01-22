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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    /**
     * 查询列表
     */
    @Override
    public List<LabelResultVo> query(long dataRowId, long taskId) {
        List<LabelResultVo> list = new ArrayList<>();
        List<LabelResultEntity> result = labelResultRepository.query(dataRowId, taskId);
        convertLabelResultInfo(result, list);
        return list;
    }

    /**
     * 新增
     *
     * @param labelIndexVos
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<LabelResultVo> insert(List<LabelIndexVo> labelIndexVos) {
        List<LabelResultEntity> list = new ArrayList<>();
        Optional<TaskInfoEntity> taskInfoOptional = taskInfoRepository.findById(labelIndexVos.get(0).getTaskId());
        Assert.isTrue(taskInfoOptional.isPresent(), "taskId信息为空！");
        TaskInfoEntity taskInfoEntity = taskInfoOptional.get();
        labelIndexVos.forEach(labelIndexVo -> {
            LabelResultEntity labelResult = new LabelResultEntity();
            labelResult.setStart(labelIndexVo.getStart());
            labelResult.setEnd(labelIndexVo.getEnd());
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
            list.add(labelResult);
        });
        List<LabelResultEntity> savedList = labelResultRepository.saveAll(list);
        //更新任务的当前标注行信息,进度信息
        taskInfoEntity.setCurrentRowId(labelIndexVos.get(0).getDataRowId());
        long finishedRowCount = labelResultRepository.getFinishedRowCount(taskInfoEntity.getId());
        BigDecimal finishedDecimal = new BigDecimal(finishedRowCount);
        BigDecimal sizeDecimal = new BigDecimal(taskInfoEntity.getSize());
        if(finishedRowCount==taskInfoEntity.getSize()){
            taskInfoEntity.setStatus(TaskStatus.FINISHED.getType());
        }else{
            taskInfoEntity.setStatus(TaskStatus.STARTING.getType());
        }
        taskInfoEntity.setProcess(finishedDecimal.divide(sizeDecimal, 4, BigDecimal.ROUND_HALF_UP));
        taskInfoRepository.save(taskInfoEntity);
        List<LabelResultVo> resultVos = new ArrayList<>();
        convertLabelResultInfo(savedList, resultVos);
        return resultVos;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(List<Long> ids){
        try {
            ids.forEach(id -> labelResultRepository.deleteById(id));
        } catch (Exception e) {
            throw e;
        }
    }

    private void convertLabelResultInfo(List<LabelResultEntity> source, List<LabelResultVo> target){
        source.forEach(labelResultEntity -> {
            LabelResultVo labelResultVo = new LabelResultVo();
            labelResultVo.setDataRow(new DataRowVo());
            labelResultVo.setTagLabel(new TagLabelVo());
            labelResultVo.setTaskInfo(new TaskInfoVo());
            //需要的属性直接拷贝，考虑日期不需要就暂不处理
            BeanUtils.copyProperties(labelResultEntity, labelResultVo);
            BeanUtils.copyProperties(labelResultEntity.getDataRow(), labelResultVo.getDataRow());
            BeanUtils.copyProperties(labelResultEntity.getTagLabel(), labelResultVo.getTagLabel());
            BeanUtils.copyProperties(labelResultEntity.getTaskInfo(), labelResultVo.getTaskInfo());
            target.add(labelResultVo);
        });
    }
}
