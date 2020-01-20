package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.vo.DataRowVo;



import java.util.List;

/**
 * 数据行业务类
 * @author Marty
 */
public interface DataRowService {
    DataRowVo detail(Long dataRowId);

    void batchInsert(List<String> contents, Long id);
}
