package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.DataSetEntity;


import com.fhpt.imageqmind.service.AbstractSyncData;
import com.fhpt.imageqmind.service.SyncDataService;

/**
 * csv文件同步业务
 * @author Marty
 */
public class SyncCsvData extends AbstractSyncData {
    @Override
    public boolean syncData(DataSetEntity dataSet) {
        return false;
    }

    @Override
    public boolean connect(String ip, int port, String userName, String password, String dbName, String tableName, String columnName, String schema) {
        return false;
    }
}
