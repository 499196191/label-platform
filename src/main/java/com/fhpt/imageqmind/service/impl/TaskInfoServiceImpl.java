package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.constant.DeleteStatus;
import com.fhpt.imageqmind.constant.RowStatus;
import com.fhpt.imageqmind.constant.TaskStatus;
import com.fhpt.imageqmind.domain.*;
import com.fhpt.imageqmind.exceptions.TagNameVerifyException;
import com.fhpt.imageqmind.exceptions.TaskNameVerifyException;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.*;
import com.fhpt.imageqmind.repository.*;
import com.fhpt.imageqmind.service.LoginService;
import com.fhpt.imageqmind.service.TaskInfoService;
import com.fhpt.imageqmind.utils.ObjectConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private LabelResultRepository labelResultRepository;
    @Autowired
    private TaskRowMapRepository taskRowMapRepository;
    @Autowired
    private LoginService loginService;
    @PersistenceContext
    private EntityManager em;

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
            taskInfoVo.setCreateTime(taskInfoEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            taskInfoVo.setProcess(taskInfoEntity.getProcess().doubleValue());
            //处理数据集信息，这里一个标注任务只能标注一个数据集
            DataSetEntity dataSetEntity = taskInfoEntity.getDataSetEntities().get(0);
            taskInfoVo.setDataSetName(dataSetEntity.getName());
            taskInfoVo.setDataSetSize(dataSetEntity.getSize());
            //处理标签信息
            List<TagLabelVo> tagLabelVos = new ArrayList<>();
            taskInfoEntity.getTagLabelEntities().forEach(tagLabelEntity -> {
                TagLabelVo tagLabelVo = new TagLabelVo();
                BeanUtils.copyProperties(tagLabelEntity, tagLabelVo);
                //查询不同标签下的标注量大小
                tagLabelVo.setTagedsize(labelResultRepository.getLabelResultSize(tagLabelEntity.getId(), taskId));
                //标签下的所有标注
                List<LabelResultEntity> labelResultEntityList = labelResultRepository.getAll(taskId, tagLabelEntity.getId());
                List<LabelResultCount> countList = new ArrayList<>();
                ObjectConvertUtil.labelResultConvertToLabelCount(labelResultEntityList, countList);
                tagLabelVo.setLabelResultCount(countList);
                tagLabelVos.add(tagLabelVo);
            });
            taskInfoVo.setTagLabels(tagLabelVos);
            //处理当前标注文章信息
            List<TaskRowMapEntity> taskRowMapEntities = taskRowMapRepository.getNext(taskId, PageRequest.of(0, 1));
            taskInfoVo.setCurrentRowId(taskRowMapEntities.size() != 0 ? taskRowMapEntities.get(0).getRowId() : -1);
            //统计任务进度信息，按照标注行信息统计
            List<LabelProcessInfo> labelProcessInfo = new ArrayList<>();
            long taskAllSize = 0;
            long taskTodaySize = 0;
            List<TaskRowMapEntity> taskRowMapEntityList = taskRowMapRepository.getAllByTaskIdAndStatus(taskId, RowStatus.LABELED.getType());
            if (taskRowMapEntityList != null && taskRowMapEntityList.size() != 0) {
                List<String> names = taskRowMapEntityList.stream().map(TaskRowMapEntity::getCreateBy).distinct().collect(Collectors.toList());

                for (String name : names) {
                    LabelProcessInfo labelProcess = new LabelProcessInfo();
                    labelProcess.setCreatedBy(name);
                    long allSize = taskRowMapEntityList.stream().filter(taskRowMapEntity -> taskRowMapEntity.getCreateBy().equals(name)).count();
                    labelProcess.setAllSize(allSize);
                    taskAllSize += allSize;
                    LocalDate today = LocalDate.now();
                    long todaySize = taskRowMapEntityList.stream().filter(taskRowMapEntity -> taskRowMapEntity.getCreateBy().equals(name) && 0 == today.compareTo(Instant.ofEpochMilli(taskRowMapEntity.getCreateTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDate())).count();
                    labelProcess.setTodaySize(todaySize);
                    taskTodaySize += todaySize;
                    TaskRowMapEntity endTaskRowMap = taskRowMapEntityList.stream().filter(taskRowMapEntity -> taskRowMapEntity.getCreateBy().equals(name)).findFirst().orElse(null);
                    TaskRowMapEntity startTaskRowMap = taskRowMapEntityList.stream().filter(taskRowMapEntity -> taskRowMapEntity.getCreateBy().equals(name)).min(Comparator.comparing(taskRowMapEntity -> taskRowMapEntity.getCreateTime())).orElse(null);
                    if (startTaskRowMap != null) {
                        labelProcess.setStartTime(startTaskRowMap.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                    if (endTaskRowMap != null) {
                        labelProcess.setEndTime(endTaskRowMap.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                    labelProcessInfo.add(labelProcess);
                }
            }
            taskInfoVo.setLabelProcessInfo(labelProcessInfo);
            taskInfoVo.setTaskAllSize(taskAllSize);
            taskInfoVo.setTaskTodaySize(taskTodaySize);
            return taskInfoVo;
        }
        return null;
    }

    @Override
    public boolean verifyName(String name) {
        if(taskInfoRepository.getCountByName(name)>0){
            throw new TaskNameVerifyException(String.format("该标注任务名称'%s'在系统中已经重名", name));
        }
        return true;
    }

    @Override
    public PageInfo<TaskInfoVo> query(Integer page, Integer pageSize, Long typeId, Integer status, String tag, String name, boolean sizeIsNotZero) {
        PageInfo<TaskInfoVo> pageInfo = new PageInfo<>();
        StringBuilder sql = new StringBuilder("select t from TaskInfoEntity t where 1=1 ");
        StringBuilder sqlCount = new StringBuilder("select count(t.id) from TaskInfoEntity t where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(name)) {
            sql.append(" and t.name like :name");
            sqlCount.append(" and t.name like :name");
            params.put("name", "%" + name + "%");
        }
        if (typeId != -1) {
            sql.append(" and t.taskType.id = :typeId");
            sqlCount.append(" and t.taskType.id = :typeId");
            params.put("typeId", typeId);
        }
        if (status != -1) {
            sql.append(" and t.status = :status");
            sqlCount.append(" and t.status = :status");
            params.put("status", status);
        }
        if (sizeIsNotZero) {
            sql.append(" and t.finishedSize !=0");
            sqlCount.append(" and t.finishedSize !=0");
        }
        if (StringUtils.hasText(tag)) {
            List<Long> tagIds = Arrays.stream(tag.split(",")).map(t -> Long.parseLong(t)).collect(Collectors.toList());
            sql.append(" and t.id in (select m.taskId from TaskTagMapEntity m where m.tagId in :tagId)");
            sqlCount.append(" and t.id in (select m.taskId from TaskTagMapEntity m where m.tagId in :tagId)");
            params.put("tagId", tagIds);
        }
        sql.append(" and t.isDelete = 0 order by t.createTime desc");
        sqlCount.append(" and t.isDelete = 0 order by t.createTime desc");
        Query query = em.createQuery(sql.toString());
        Query queryCount = em.createQuery(sqlCount.toString());
        params.forEach((k, v) -> {
            query.setParameter(k, v);
            queryCount.setParameter(k, v);
        });
        List<TaskInfoEntity> list = (List<TaskInfoEntity>) query.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList();
        long count = (Long)queryCount.getSingleResult();
        pageInfo.setTotal(count);
        List<TaskInfoVo> listResult = new ArrayList<>();
        list.forEach(taskInfoEntity -> {
            TaskInfoVo taskInfoVo = new TaskInfoVo();
            BeanUtils.copyProperties(taskInfoEntity, taskInfoVo);
            taskInfoVo.setStatusName(TaskStatus.getTypeName(taskInfoEntity.getStatus()));
            taskInfoVo.setProcess(taskInfoEntity.getProcess().doubleValue());
            taskInfoVo.setCreateTime(taskInfoEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            taskInfoVo.setUpdateTime(taskInfoEntity.getUpdateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            //处理任务类型信息
            TaskTypeVo taskTypeVo = new TaskTypeVo();
            BeanUtils.copyProperties(taskInfoEntity.getTaskType(), taskTypeVo);
            taskInfoVo.setTaskType(taskTypeVo);
            taskInfoVo.setTaskTypeName(taskTypeVo.getName());
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
        pageInfo.setList(listResult);
        return pageInfo;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public long add(AddTaskVo addTaskVo) {
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity();
        taskInfoEntity.setName(addTaskVo.getName());
        taskInfoEntity.setStatus(TaskStatus.NOT_START.getType());
        Optional<TaskTypeEntity> taskTypeEntityOptional = taskTypeRepository.findById(addTaskVo.getTaskType());
        if (!taskTypeEntityOptional.isPresent()) {
            return -1;
        }
        taskInfoEntity.setTaskType(taskTypeEntityOptional.get());
        Timestamp now = Timestamp.from(Instant.now());
        taskInfoEntity.setCreateTime(now);
        taskInfoEntity.setUpdateTime(now);
        taskInfoEntity.setCreatedBy(loginService.getLoginUserName());
        taskInfoEntity.setIsDelete(DeleteStatus.NOT_DELETE.getType());
        List<DataSetEntity> dataSetEntities = new ArrayList<>();
        List<TagLabelEntity> tagLabelEntities = new ArrayList<>();
        //只需传递id至临时实体，将自动保存映射map关系(暂时只考虑一个数据集的标注)
        DataSetEntity dataSetEntity = new DataSetEntity();
        dataSetEntity.setId(addTaskVo.getDataSetId());
        Optional<DataSetEntity> dataSetEntityOptional = dataSetRepository.findById(addTaskVo.getDataSetId());
        if (!dataSetEntityOptional.isPresent()) {
            return -1;
        }
        List<DataRowEntity> dataRowEntities = dataSetEntityOptional.get().getDataRowEntities();
        //初始化任务大小
        taskInfoEntity.setSize(addTaskVo.getSize());
        taskInfoEntity.setFinishedSize(0);
        //初始进度为0.00
        taskInfoEntity.setProcess(new BigDecimal(0));
        //初始标注行id
        taskInfoEntity.setCurrentRowId(dataRowEntities.get(0).getId());
        dataSetEntities.add(dataSetEntity);
        addTaskVo.getTagIds().forEach(tagId -> {
            TagLabelEntity tagLabelEntity = new TagLabelEntity();
            tagLabelEntity.setId(tagId);
            tagLabelEntities.add(tagLabelEntity);
        });
        taskInfoEntity.setDataSetEntities(dataSetEntities);
        taskInfoEntity.setTagLabelEntities(tagLabelEntities);
        taskInfoRepository.save(taskInfoEntity);
        //同步数据集下面每一篇文章的
        List<TaskRowMapEntity> taskRowMapEntities = new ArrayList<>();
        dataRowEntities.forEach(dataRowEntity -> {
            TaskRowMapEntity taskRowMapEntity = new TaskRowMapEntity();
            taskRowMapEntity.setRowId(dataRowEntity.getId());
            taskRowMapEntity.setTaskId(taskInfoEntity.getId());
            taskRowMapEntity.setStatus(RowStatus.UNLABELED.getType());
            taskRowMapEntity.setCreateTime(now);
            taskRowMapEntity.setUpdateTime(now);
            taskRowMapEntities.add(taskRowMapEntity);
        });
        taskRowMapRepository.saveAll(taskRowMapEntities);
        return taskInfoEntity.getId();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(Long taskId) {
//        taskInfoRepository.deleteById(taskId);
//        List<LabelResultEntity> list = labelResultRepository.getAll(taskId);
//        labelResultRepository.deleteAll(list);
        //这里使用伪删除
        Optional<TaskInfoEntity> optionalTaskInfoEntity = taskInfoRepository.findById(taskId);
        if (optionalTaskInfoEntity.isPresent()) {
            TaskInfoEntity taskInfoEntity = optionalTaskInfoEntity.get();
            taskInfoEntity.setIsDelete(DeleteStatus.DELETE.getType());
            taskInfoRepository.save(taskInfoEntity);
            return true;
        } else {
            return false;
        }
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean stop(Long taskId) {
        Optional<TaskInfoEntity> taskInfoEntityOptional = taskInfoRepository.findById(taskId);
        if (taskInfoEntityOptional.isPresent()) {
            TaskInfoEntity taskInfoEntity = taskInfoEntityOptional.get();
            taskInfoEntity.setStatus(TaskStatus.STOPPED.getType());
            taskInfoRepository.save(taskInfoEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean restart(Long taskId) {
        Optional<TaskInfoEntity> taskInfoEntityOptional = taskInfoRepository.findById(taskId);
        if (taskInfoEntityOptional.isPresent()) {
            TaskInfoEntity taskInfoEntity = taskInfoEntityOptional.get();
            taskInfoEntity.setStatus(TaskStatus.STARTING.getType());
            taskInfoRepository.save(taskInfoEntity);
            return true;
        }
        return false;
    }


}
