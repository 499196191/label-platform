package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.config.SystemProperties;

import com.fhpt.imageqmind.constant.DeleteStatus;
import com.fhpt.imageqmind.constant.SourceType;

import com.fhpt.imageqmind.constant.TaskStatus;
import com.fhpt.imageqmind.domain.*;

import com.fhpt.imageqmind.exceptions.DataSetNameVerifyException;
import com.fhpt.imageqmind.factory.SyncServiceBeanFactory;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.*;
import com.fhpt.imageqmind.repository.DataSetRepository;



import com.fhpt.imageqmind.repository.LabelResultRepository;
import com.fhpt.imageqmind.repository.TaskInfoRepository;
import com.fhpt.imageqmind.service.DataSetService;
import com.fhpt.imageqmind.service.SyncDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据集业务接口实现
 * @author Marty
 */
@Component
public class DataSetServiceImpl implements DataSetService {

    @Autowired
    private DataSetRepository dataSetRepository;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private TaskInfoRepository taskInfoRepository;
    @Autowired
    private LabelResultRepository labelResultRepository;
    @PersistenceContext
    private EntityManager em;

    /**
     * 查询列表
     */
    @Override
    public PageInfo<DataSetVo> query(int page, int pageSize, int sourceType, String typeNames, String name) {
        StringBuilder sql = new StringBuilder("select d from DataSetEntity d where 1=1 ");
        StringBuilder sqlCount = new StringBuilder("select count(d.id) from DataSetEntity d where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(name)) {
            sql.append(" and d.name like :name");
            sqlCount.append(" and d.name like :name");
            params.put("name", "%" + name + "%");
        }
        if (sourceType != -1) {
            sql.append(" and d.sourceType = :sourceType");
            sqlCount.append(" and d.sourceType = :sourceType");
            params.put("sourceType", sourceType);
        }
        if(StringUtils.hasText(typeNames)){
            sql.append(" and (");
            sqlCount.append(" and (");
            String[] typeNamesArray = typeNames.split(",");
            for (int i = 0; i < typeNamesArray.length; i++) {
                String typeName = typeNamesArray[i];
                if (i == 0) {
                    sql.append(" d.typeNames like '%" + typeName + "%'");
                    sqlCount.append(" d.typeNames like '%" + typeName + "%'");
                } else {
                    sql.append(" or d.typeNames like '%" + typeName + "%'");
                    sqlCount.append(" or d.typeNames like '%" + typeName + "%'");
                }
            }
            sql.append(" ) ");
            sqlCount.append(" ) ");
        }
        sql.append(" and d.isDelete = 0 order by d.createTime desc");
        sqlCount.append(" and d.isDelete = 0 order by d.createTime desc");
        Query query = em.createQuery(sql.toString());
        Query queryCount = em.createQuery(sqlCount.toString());
        params.forEach((k, v) -> {
            query.setParameter(k, v);
            queryCount.setParameter(k, v);
        });
        List<DataSetEntity> list;
        if (pageSize == -1) {
            list = (List<DataSetEntity>) query.getResultList();
        } else {
            list = (List<DataSetEntity>) query.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList();
        }
        long count = (Long)queryCount.getSingleResult();
        PageInfo result = new PageInfo();
        result.setTotal(count);
        List<DataSetVo> info = list.stream().map(dataSetEntity -> {
            DataSetVo dataSetVo = new DataSetVo();
            dataSetVo.setId(dataSetEntity.getId());
            dataSetVo.setName(dataSetEntity.getName());
            dataSetVo.setSize(dataSetEntity.getSize());
            dataSetVo.setDescribe(dataSetEntity.getDescription() == null ? "" : dataSetEntity.getDescription());
            dataSetVo.setCreateTime(dataSetEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dataSetVo.setUpdateTime(dataSetEntity.getUpdateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dataSetVo.setType(dataSetEntity.getSourceType());
            SourceType sourceTypeEnum = SourceType.getByType(dataSetEntity.getSourceType());
            if (sourceTypeEnum == SourceType.ORACLE || sourceTypeEnum == SourceType.MYSQL) {
                DbInfoVo dbInfoVo = new DbInfoVo();
                dbInfoVo.setIp(dataSetEntity.getDbInfo().getIp());
                dbInfoVo.setConnectName(dataSetEntity.getDbInfo().getConnectName());
                dbInfoVo.setPort(dataSetEntity.getDbInfo().getPort());
                dbInfoVo.setUserName(dataSetEntity.getDbInfo().getUserName());
                dbInfoVo.setPassword(dataSetEntity.getDbInfo().getPassword());
                dataSetVo.setDbInfo(dbInfoVo);
                dataSetVo.setColumnName(dataSetEntity.getColumnName());
            } else if (sourceTypeEnum == SourceType.Excel || sourceTypeEnum == SourceType.CSV) {
                FileInfoVo fileInfoVo = new FileInfoVo();
                fileInfoVo.setSheetName(dataSetEntity.getFileInfo().getSheetName());
                fileInfoVo.setPath(dataSetEntity.getFileInfo().getPath());
                dataSetVo.setFileInfo(fileInfoVo);
            }
            //关联任务
            dataSetVo.setRelateTaskNum(taskInfoRepository.getCountByDataSetId(dataSetEntity.getId()));
            List<TaskInfoVo> taskInfoVos = new ArrayList<>();
            List<TaskInfoEntity> taskInfoEntities = taskInfoRepository.getAll(dataSetEntity.getId());
            if (taskInfoEntities != null) {
                taskInfoEntities.forEach(taskInfoEntity -> {
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
                    taskInfoVo.setTagNames(taskInfoEntity.getTagLabelEntities().stream().map(TagLabelEntity::getName).collect(Collectors.joining(",")));
                    taskInfoVos.add(taskInfoVo);
                });
            }
            dataSetVo.setTaskInfoVos(taskInfoVos);
            return dataSetVo;
        }).collect(Collectors.toList());
        result.setList(info);
        return result;
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public DataSetVo insert(DataSetVo dataSetVo) {
        //保存数据集信息
        DataSetEntity dataSetEntity = new DataSetEntity();
        dataSetEntity.setName(dataSetVo.getName());
        dataSetEntity.setDescription(dataSetVo.getDescribe());
        Timestamp now = Timestamp.from(Instant.now());
        dataSetEntity.setCreateTime(now);
        dataSetEntity.setUpdateTime(now);
        dataSetEntity.setSourceType(dataSetVo.getType());
        dataSetEntity.setColumnName(dataSetVo.getColumnName());
        dataSetEntity.setTypeNames(dataSetVo.getTypeNames() == null ? "" : dataSetVo.getTypeNames());
        dataSetEntity.setMaxSize(systemProperties.getSyncNum());
        dataSetEntity.setIsDelete(DeleteStatus.NOT_DELETE.getType());
        SourceType sourceType = SourceType.getByType(dataSetVo.getType());
        if (sourceType == SourceType.MYSQL || sourceType == SourceType.ORACLE || sourceType == SourceType.PG) {
            //保存mysql/oracle信息
            DbInfoVo dbInfoVo = dataSetVo.getDbInfo();
            if (dbInfoVo != null) {
                DbInfoEntity dbInfoEntity = new DbInfoEntity();
                dbInfoEntity.setConnectName(dbInfoVo.getConnectName());
                dbInfoEntity.setCreateTime(now);
                dbInfoEntity.setUpdateTime(now);
                dbInfoEntity.setIp(dbInfoVo.getIp());
                dbInfoEntity.setPort(dbInfoVo.getPort());
                dbInfoEntity.setUserName(dbInfoVo.getUserName());
                dbInfoEntity.setPassword(dbInfoVo.getPassword());
                dbInfoEntity.setDbName(dbInfoVo.getDbName());
                dbInfoEntity.setTableName(dbInfoVo.getTableName());
                if (StringUtils.hasText(dbInfoVo.getSchema())) {
                    dbInfoEntity.setSchema(StringUtils.trimAllWhitespace(dbInfoVo.getSchema()));
                } else {
                    dbInfoEntity.setSchema("");
                }
                dataSetEntity.setDbInfo(dbInfoEntity);
            }
        } else if (sourceType == SourceType.Excel) {
            FileInfoVo fileInfoVo = dataSetVo.getFileInfo();
            //保存文件信息
            FileInfoEntity fileInfoEntity = new FileInfoEntity();
            fileInfoEntity.setPath(fileInfoVo.getPath());
            fileInfoEntity.setSheetName(fileInfoVo.getSheetName());
            fileInfoEntity.setCreateTime(now);
            fileInfoEntity.setUpdateTime(now);
            dataSetEntity.setFileInfo(fileInfoEntity);
        }
        dataSetRepository.save(dataSetEntity);
        dataSetVo.setId(dataSetEntity.getId());
        //如果是数据库数据源或者文件数据，同步数据至本系统
        syncData(dataSetEntity.getId());
        return dataSetVo;
    }

    @Override
    public boolean verifyName(String name) {
        if (dataSetRepository.getCountByName(name) > 0) {
            throw new DataSetNameVerifyException(String.format("该系统中已经有名称为'%s'的数据集", name));
        }
//        else if (dataSetRepository.getCountByNameDeleted(name) > 0) {
//            throw new DataSetNameVerifyException(String.format("该系统中删除的列表已经存在名称为'%s'的数据集", name));
//        }
        return true;
    }

    /**
     * 更新
     *
     * @param dataSetVo
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public DataSetVo update(DataSetVo dataSetVo) {
        DataSetEntity dataSetEntity = new DataSetEntity();
        dataSetEntity.setName(dataSetVo.getName());
        dataSetEntity.setUpdateTime(Timestamp.from(Instant.now()));
        dataSetEntity.setSourceType(dataSetVo.getType());
        dataSetEntity.setId(dataSetVo.getId());
        DbInfoEntity dbInfoEntity = new DbInfoEntity();
        dbInfoEntity.setIp(dataSetVo.getDbInfo().getIp());
        dbInfoEntity.setPort(dataSetVo.getDbInfo().getPort());
        dbInfoEntity.setUserName(dataSetVo.getDbInfo().getUserName());
        dbInfoEntity.setPassword(dataSetVo.getDbInfo().getPassword());
        dbInfoEntity.setUpdateTime(Timestamp.from(Instant.now()));
        dataSetEntity.setDbInfo(dbInfoEntity);
        dataSetRepository.save(dataSetEntity);
        return dataSetVo;
    }

    @Override
    public boolean connectDB(int sourceType, String ip, int port, String userName, String password, String dbName, String tableName, String columnName, String schema) {
        SourceType sourceTypeEnum = SourceType.getByType(sourceType);
        SyncDataService syncDataService = SyncServiceBeanFactory.getInstance(sourceTypeEnum);
        //执行连接测试
        return syncDataService.connect(ip, port, userName, password, dbName, tableName, columnName, schema);
    }

    /**
     * 更新
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(Long id, String name, String typeNames, String describe) {
        Optional<DataSetEntity> entityOptional = dataSetRepository.findById(id);
        if (entityOptional.isPresent()) {
            DataSetEntity dataSetEntity = entityOptional.get();
            dataSetEntity.setName(name);
            dataSetEntity.setTypeNames(typeNames);
            dataSetEntity.setDescription(describe);
            dataSetEntity.setUpdateTime(Timestamp.from(Instant.now()));
            dataSetRepository.save(dataSetEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(long id) {
        //            dataSetRepository.deleteById(id);
        //            List<TaskInfoEntity> taskInfoEntityList = taskInfoRepository.getAll(id);
        //            if (taskInfoEntityList != null) {
        //                //清除已有的标注
        //                taskInfoEntityList.forEach(taskInfoEntity -> {
        //                    List<LabelResultEntity> list = labelResultRepository.getAll(taskInfoEntity.getId());
        //                    labelResultRepository.deleteAll(list);
        //                });
        //                //删除数据集下面的任务
        //                taskInfoRepository.deleteAll(taskInfoEntityList);
        //            }
        //这里使用伪删除
        Optional<DataSetEntity> optionalDataSetEntity = dataSetRepository.findById(id);
        if (!optionalDataSetEntity.isPresent()) {
            return false;
        } else {
            DataSetEntity dataSetEntity = optionalDataSetEntity.get();
            dataSetEntity.setIsDelete(DeleteStatus.DELETE.getType());
            dataSetRepository.save(dataSetEntity);
            return true;
        }
    }

    @Override
    public boolean syncData(Long id) {
        Optional<DataSetEntity> entityOptional = dataSetRepository.findById(id);
        if (entityOptional.isPresent()) {
            DataSetEntity dataSetEntity = entityOptional.get();
            SourceType sourceType = SourceType.getByType(dataSetEntity.getSourceType());
            SyncDataService syncDataService = SyncServiceBeanFactory.getInstance(sourceType);
            //执行数据同步
            boolean success = syncDataService.syncData(dataSetEntity);
            return success;
        }
        return false;
    }

    @Override
    public Set<String> getAllTypeNames() {
        List<DataSetEntity> list = dataSetRepository.findAll();
        Set<String> typeNames = new HashSet<>();
        list.forEach(dataSetEntity -> {
            String typeName = dataSetEntity.getTypeNames();
            if (StringUtils.hasText(typeName)) {
                String[] typeArray = typeName.split(",");
                typeNames.addAll(Arrays.asList(typeArray));
            }
        });
        return typeNames;
    }

    @Override
    public Set<String> getTypeNamesByIds(List<Long> ids) {
        List<DataSetEntity> list = dataSetRepository.findAllById(ids);
        Set<String> typeNames = new HashSet<>();
        list.forEach(dataSetEntity -> {
            String typeName = dataSetEntity.getTypeNames();
            if (StringUtils.hasText(typeName)) {
                String[] typeArray = typeName.split(",");
                typeNames.addAll(Arrays.asList(typeArray));
            }
        });
        return typeNames;
    }

    @Override
    public DataSetEntity view(long id) {
        return dataSetRepository.getOne(id);
    }

}
