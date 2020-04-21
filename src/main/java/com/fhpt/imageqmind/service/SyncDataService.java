package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.domain.DataSetEntity;

/**
 * 同步任务接口
 * @author Marty
 */
public interface SyncDataService {

    boolean syncData(DataSetEntity dataSet);

    boolean connect(String ip, int port, String userName, String password, String dbName, String tableName, String columnName, String schema);

}
