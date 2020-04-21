package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.domain.ModelInfoEntity;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.AddModelVo;
import com.fhpt.imageqmind.objects.vo.AddRuleModelVo;
import com.fhpt.imageqmind.objects.vo.ModelInfoVo;

/**
 * 模型业务基类
 * @author Marty
 */
public interface ModelInfoService {

    boolean add(AddModelVo addModelVo);

    boolean add(AddRuleModelVo addRuleModelVo);

    PageInfo<ModelInfoVo> query(String modelName, Integer modelType,Integer sourceType,String typesName,Integer deployStatus, Integer page, Integer pageSize);

    boolean update(long id,Integer deployStatus,String modelName,String typesName, String version,String brief);

    ModelInfoEntity view(long id);
    boolean delete(long id);
    boolean del(long id);
}
