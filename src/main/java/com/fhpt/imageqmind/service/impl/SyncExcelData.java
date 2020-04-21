package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.DataSetEntity;
import com.fhpt.imageqmind.domain.FileInfoEntity;

import com.fhpt.imageqmind.repository.DataSetRepository;
import com.fhpt.imageqmind.service.AbstractSyncData;

import com.fhpt.imageqmind.utils.ExcelUtil;
import com.fhpt.imageqmind.utils.MinioUtil;
import com.fhpt.imageqmind.utils.SpringContextUtil;

import java.io.InputStream;
import java.util.List;

/**
 * excel文件数据同步
 * @author Marty
 */
public class SyncExcelData extends AbstractSyncData {

    @Override
    public boolean syncData(DataSetEntity dataSet) {
        FileInfoEntity fileInfo = dataSet.getFileInfo();
        InputStream excelStream = MinioUtil.getObject(fileInfo.getPath());
        List<String> contents = ExcelUtil.readContentByCondition(excelStream, fileInfo.getPath(), fileInfo.getSheetName(), dataSet.getColumnName());
        //用户定义最大同步量
        if (contents.size() > dataSet.getMaxSize()) {
            contents = contents.subList(0, dataSet.getMaxSize() - 1);
        }
        //更新数据量
        DataSetRepository dataSetRepository = SpringContextUtil.getBeanByClass(DataSetRepository.class);
        dataSet.setSize(contents.size());
        dataSetRepository.save(dataSet);
        return saveToDB(contents, dataSet.getId());
    }



    @Override
    public boolean connect(String ip, int port, String userName, String password, String dbName, String tableName, String columnName, String schema) {
        return false;
    }

}
