package com.fhpt.imageqmind.factory;

import com.fhpt.imageqmind.constant.SourceType;
import com.fhpt.imageqmind.service.SyncDataService;
import com.fhpt.imageqmind.service.impl.SyncCsvData;
import com.fhpt.imageqmind.service.impl.SyncExcelData;
import com.fhpt.imageqmind.service.impl.SyncMysqlData;
import com.fhpt.imageqmind.service.impl.SyncOracleData;

/**
 * 数据同步业务Bean工厂
 * @author Marty
 */
public class SyncServiceBeanFactory {

    /**
     * 根据数据源类型自动实例化对象
     * @param sourceType
     * @return
     */
    public static SyncDataService getInstance(SourceType sourceType) {
        switch (sourceType) {
            case MYSQL:
                return new SyncMysqlData();
            case ORACLE:
                return new SyncOracleData();
            case CSV:
                return new SyncCsvData();
            case Excel:
                return new SyncExcelData();
        }
        return null;
    }
}
