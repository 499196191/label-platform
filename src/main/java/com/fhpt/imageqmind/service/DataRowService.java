package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.constant.RowStatus;

import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.vo.DataRowVo;
import com.fhpt.imageqmind.objects.vo.DataSetDetail;
import com.fhpt.imageqmind.objects.vo.DataSetVo;


import java.util.List;

/**
 * 数据行业务类
 * @author Marty
 */
public interface DataRowService {
    DataRowVo detail(Long dataRowId, Long taskId);

    void batchInsert(List<String> contents, Long id);

    DataSetDetail query(Long dataSetId, int page, int pageSize);

    long updateDataRowMapStatus(Long dataRowId, Long taskId, RowStatus rowStatus);

    long getNext(Long taskId);
}
