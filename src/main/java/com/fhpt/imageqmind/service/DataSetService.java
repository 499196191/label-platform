package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.DataSetVo;

/**
 * 数据集业务接口
 * @author Marty
 */
public interface DataSetService {
    /**
     * 查询列表
     */
    PageInfo<DataSetVo> query(int page, int pageSize);

    /**
     * 新增
     */
    DataSetVo insert(DataSetVo dataSetVo);

    /**
     * 更新
     */
    DataSetVo update(DataSetVo dataSetVo);

    /**
     * 更新
     */
    boolean update(Long id, String name, String ip, Integer port, String username, String password);

    boolean delete(long id);

    boolean syncData(Long id);
}
