package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.utils.SpringContextUtil;
import java.util.List;

/**
 * 抽象拓展类
 * @author Marty
 */
public abstract class AbstractSyncData implements SyncDataService {

    protected boolean saveToDB(List<String> rows, Long dataSetId){
        DataRowService dataRowService = SpringContextUtil.getBeanByClass(DataRowService.class);
        try {
            dataRowService.batchInsert(rows, dataSetId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
