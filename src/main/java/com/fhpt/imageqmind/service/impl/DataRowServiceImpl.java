package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.DataRowEntity;

import com.fhpt.imageqmind.domain.DataSetEntity;
import com.fhpt.imageqmind.objects.vo.DataRowVo;
import com.fhpt.imageqmind.repository.DataRowRepository;

import com.fhpt.imageqmind.repository.DataSetRepository;
import com.fhpt.imageqmind.service.DataRowService;

import com.fhpt.imageqmind.service.DataSetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


import java.util.stream.Collectors;

/**
 * 数据行业务实现
 * @author Marty
 */
@Service
public class DataRowServiceImpl implements DataRowService {

    @Autowired
    private DataRowRepository dataRowRepository;
    @Autowired
    private DataSetRepository dataSetRepository;

    @Override
    public DataRowVo detail(Long dataRowId) {
        Optional<DataRowEntity> dataRowEntityOptional = dataRowRepository.findById(dataRowId);
        if (dataRowEntityOptional.isPresent()) {
            DataRowEntity dataRowEntity = dataRowEntityOptional.get();
            DataRowVo dataRowVo = new DataRowVo();
            BeanUtils.copyProperties(dataRowEntity, dataRowVo);
            return dataRowVo;
        }
        return null;
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
}
