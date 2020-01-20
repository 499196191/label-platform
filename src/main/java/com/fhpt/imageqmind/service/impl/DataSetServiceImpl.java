package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.constant.SourceType;

import com.fhpt.imageqmind.domain.DataSetEntity;
import com.fhpt.imageqmind.domain.DbInfoEntity;

import com.fhpt.imageqmind.factory.SyncServiceBeanFactory;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.DataSetVo;
import com.fhpt.imageqmind.objects.vo.DbInfoVo;
import com.fhpt.imageqmind.repository.DataSetRepository;
import com.fhpt.imageqmind.repository.DbInfoRepository;
import com.fhpt.imageqmind.service.DataSetService;

import com.fhpt.imageqmind.service.SyncDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
    private DbInfoRepository dbInfoRepository;

    /**
     * 查询列表
     */
    @Override
    public PageInfo<DataSetVo> query(int page, int pageSize) {
        Page<DataSetEntity> list = dataSetRepository.findAll(PageRequest.of(page - 1, pageSize));
        PageInfo result = new PageInfo();
        result.setTotal(list.getTotalElements());
        List<DataSetVo> info = list.getContent().stream().map(dataSetEntity -> {
            DataSetVo dataSetVo = new DataSetVo();
            dataSetVo.setId(dataSetEntity.getId());
            dataSetVo.setName(dataSetEntity.getName());
            dataSetVo.setDescribe(dataSetEntity.getDescribe());
            dataSetVo.setCreateTime(dataSetEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dataSetVo.setUpdateTime(dataSetEntity.getUpdateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dataSetVo.setType(dataSetEntity.getSourceType());
            DbInfoVo dbInfoVo = new DbInfoVo();
            dbInfoVo.setIp(dataSetEntity.getDbInfo().getIp());
            dbInfoVo.setPort(dataSetEntity.getDbInfo().getPort());
            dbInfoVo.setUsername(dataSetEntity.getDbInfo().getUsername());
            dbInfoVo.setPassword(dataSetEntity.getDbInfo().getPassword());
            dataSetVo.setDbInfo(dbInfoVo);
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
        dataSetEntity.setDescribe(dataSetVo.getDescribe());
        Timestamp now = Timestamp.from(Instant.now());
        dataSetEntity.setCreateTime(now);
        dataSetEntity.setUpdateTime(now);
        dataSetEntity.setSourceType(dataSetVo.getType());
        dataSetEntity.setColumnName(dataSetVo.getColumnName());
        //保存数据源信息
        DbInfoVo dbInfoVo = dataSetVo.getDbInfo();
        if (dbInfoVo != null) {
            DbInfoEntity dbInfoEntity = new DbInfoEntity();
            dbInfoEntity.setCreateTime(now);
            dbInfoEntity.setUpdateTime(now);
            dbInfoEntity.setIp(dbInfoVo.getIp());
            dbInfoEntity.setPort(dbInfoVo.getPort());
            dbInfoEntity.setUsername(dbInfoVo.getUsername());
            dbInfoEntity.setPassword(dbInfoVo.getPassword());
            dataSetEntity.setDbInfo(dbInfoEntity);
        }
        dataSetRepository.save(dataSetEntity);
        dataSetVo.setId(dataSetEntity.getId());
        return dataSetVo;
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
        dbInfoEntity.setUsername(dataSetVo.getDbInfo().getUsername());
        dbInfoEntity.setPassword(dataSetVo.getDbInfo().getPassword());
        dbInfoEntity.setUpdateTime(Timestamp.from(Instant.now()));
        dataSetEntity.setDbInfo(dbInfoEntity);
        dataSetRepository.save(dataSetEntity);
        return dataSetVo;
    }


    /**
     * 更新
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(Long id, String name, String ip, Integer port, String username, String password) {
        Optional<DataSetEntity> entityOptional = dataSetRepository.findById(id);
        if (entityOptional.isPresent()) {
            DataSetEntity dataSetEntity = entityOptional.get();
            dataSetEntity.setName(name);
            dataSetEntity.setUpdateTime(Timestamp.from(Instant.now()));
            DbInfoEntity dbInfoEntity = dataSetEntity.getDbInfo();
            dbInfoEntity.setIp(ip);
            dbInfoEntity.setPort(port);
            dbInfoEntity.setUsername(username);
            dbInfoEntity.setPassword(password);
            dbInfoEntity.setUpdateTime(Timestamp.from(Instant.now()));
            dataSetRepository.save(dataSetEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean delete(long id) {
        try {
            dataSetRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean syncData(Long id) {
        Optional<DataSetEntity> entityOptional = dataSetRepository.findById(id);
        if (entityOptional.isPresent()) {
            DataSetEntity dataSetEntity = entityOptional.get();
            DbInfoEntity dbInfoEntity = dataSetEntity.getDbInfo();
            SourceType sourceType = SourceType.getByType(dataSetEntity.getSourceType());
            SyncDataService syncDataService = SyncServiceBeanFactory.getInstance(sourceType);
            //执行数据同步
            boolean success = syncDataService.syncData(dataSetEntity);
            return success;
        }
        return false;
    }

}
