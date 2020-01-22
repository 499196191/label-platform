package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.constant.TaskStatus;
import com.fhpt.imageqmind.domain.DataSetEntity;
import com.fhpt.imageqmind.domain.TagLabelEntity;
import com.fhpt.imageqmind.domain.TaskInfoEntity;
import com.fhpt.imageqmind.domain.TaskTypeEntity;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.AddTaskVo;
import com.fhpt.imageqmind.objects.vo.TagLabelVo;
import com.fhpt.imageqmind.objects.vo.TaskInfoVo;
import com.fhpt.imageqmind.objects.vo.TaskTypeVo;

import com.fhpt.imageqmind.repository.DataSetRepository;
import com.fhpt.imageqmind.repository.TaskInfoRepository;
import com.fhpt.imageqmind.repository.TaskTypeRepository;
import com.fhpt.imageqmind.service.TaskInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 任务信息业务
 * @author Marty
 */
@Service
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private TaskInfoRepository taskInfoRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private DataSetRepository dataSetRepository;

    @Override
    public TaskInfoVo detail(Long taskId) {
        Optional<TaskInfoEntity> taskInfoEntityOptional = taskInfoRepository.findById(taskId);
        if (taskInfoEntityOptional.isPresent()) {
            TaskInfoEntity taskInfoEntity = taskInfoEntityOptional.get();
            TaskInfoVo taskInfoVo = new TaskInfoVo();
            BeanUtils.copyProperties(taskInfoEntity, taskInfoVo);
            //处理任务类型信息
            TaskTypeVo taskTypeVo = new TaskTypeVo();
            BeanUtils.copyProperties(taskInfoEntity.getTaskType(), taskTypeVo);
            taskInfoVo.setTaskType(taskTypeVo);
            //处理标签信息
            List<TagLabelVo> tagLabelVos = new ArrayList<>();
            taskInfoEntity.getTagLabelEntities().forEach(tagLabelEntity -> {
                TagLabelVo tagLabelVo = new TagLabelVo();
                BeanUtils.copyProperties(tagLabelEntity, tagLabelVo);
                tagLabelVos.add(tagLabelVo);
            });
            taskInfoVo.setTagLabels(tagLabelVos);
            return taskInfoVo;
        }
        return null;
    }

    @Override
    public PageInfo<TaskInfoVo> query(Integer page, Integer pageSize) {
        Page<TaskInfoEntity> list = taskInfoRepository.findAll(PageRequest.of(page - 1, pageSize));
        PageInfo result = new PageInfo();
        result.setTotal(list.getTotalElements());
        List<TaskInfoEntity> taskInfoEntities = list.getContent();
        List<TaskInfoVo> listResult = new ArrayList<>();
        taskInfoEntities.forEach(taskInfoEntity -> {
            TaskInfoVo taskInfoVo = new TaskInfoVo();
            BeanUtils.copyProperties(taskInfoEntity, taskInfoVo);
            //处理任务类型信息
            TaskTypeVo taskTypeVo = new TaskTypeVo();
            BeanUtils.copyProperties(taskInfoEntity.getTaskType(), taskTypeVo);
            taskInfoVo.setTaskType(taskTypeVo);
            //处理标签信息
            List<TagLabelVo> tagLabelVos = new ArrayList<>();
            taskInfoEntity.getTagLabelEntities().forEach(tagLabelEntity -> {
                TagLabelVo tagLabelVo = new TagLabelVo();
                BeanUtils.copyProperties(tagLabelEntity, tagLabelVo);
                tagLabelVos.add(tagLabelVo);
            });
            taskInfoVo.setTagLabels(tagLabelVos);
            listResult.add(taskInfoVo);
        });
        result.setList(listResult);
        return result;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TaskInfoVo add(AddTaskVo addTaskVo) {
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setName(addTaskVo.getName());
        taskInfoEntity.setStatus(TaskStatus.NOT_START.getType());
        Optional<TaskTypeEntity> taskTypeEntityOptional = taskTypeRepository.findById(addTaskVo.getTaskType());
        if (!taskTypeEntityOptional.isPresent()) {
            return null;
        }
        taskInfoEntity.setTaskType(taskTypeEntityOptional.get());
        Timestamp now = Timestamp.from(Instant.now());
        taskInfoEntity.setCreateTime(now);
        taskInfoEntity.setUpdateTime(now);
        List<DataSetEntity> dataSetEntities = new ArrayList<>();
        List<TagLabelEntity> tagLabelEntities = new ArrayList<>();
        //只需传递id至临时实体，将自动保存映射map关系
        DataSetEntity dataSetEntity = new DataSetEntity();
        dataSetEntity.setId(addTaskVo.getDataSetId());
        Optional<DataSetEntity> dataSetEntityOptional = dataSetRepository.findById(addTaskVo.getDataSetId());
        if (!dataSetEntityOptional.isPresent()) {
            return null;
        }
        //初始化任务大小
        taskInfoEntity.setSize(dataSetEntityOptional.get().getSize());
        //初始进度为0.00
        taskInfoEntity.setProcess(new BigDecimal(0));
        //初始标注行id
        taskInfoEntity.setCurrentRowId(dataSetEntityOptional.get().getDataRowEntities().get(0).getId());
        dataSetEntities.add(dataSetEntity);
//        addTaskVo.getDataSetIds().forEach(dataSetId -> {
//            DataSetEntity dataSetEntity = new DataSetEntity();
//            dataSetEntity.setId(dataSetId);
//            dataSetEntities.add(dataSetEntity);
//        });
        addTaskVo.getTagIds().forEach(tagId -> {
            TagLabelEntity tagLabelEntity = new TagLabelEntity();
            tagLabelEntity.setId(tagId);
            tagLabelEntities.add(tagLabelEntity);
        });
        taskInfoEntity.setDataSetEntities(dataSetEntities);
        taskInfoEntity.setTagLabelEntities(tagLabelEntities);
        taskInfoRepository.save(taskInfoEntity);
        return null;
    }


}
