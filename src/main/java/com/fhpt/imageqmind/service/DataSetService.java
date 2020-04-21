package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.domain.DataSetEntity;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.DataSetVo;
import java.util.List;
import java.util.Set;

/**
 * 数据集业务接口
 * @author Marty
 */
public interface DataSetService {
    /**
     * 查询列表
     */
    PageInfo<DataSetVo> query(int page, int pageSize, int sourceType, String typeNames, String name);

    /**
     * 新增
     */
    DataSetVo insert(DataSetVo dataSetVo);

    /**
     * 更新
     */
    DataSetVo update(DataSetVo dataSetVo);

    boolean connectDB(int sourceType, String ip, int port, String userName, String password, String dbName, String tableName, String columnName, String schema);

    /**
     * 更新
     */
    boolean update(Long id, String name, String typeNames, String describe);

    boolean delete(long id);

    boolean syncData(Long id);

    Set<String> getAllTypeNames();

    Set<String> getTypeNamesByIds(List<Long> ids);
    DataSetEntity view(long id);

}
