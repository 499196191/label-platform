package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.vo.LabelIndexVo;
import com.fhpt.imageqmind.objects.vo.LabelResultVo;

import java.util.List;

/**
 * 数据行标注业务类
 */
public interface LabelResultService {
    /**
     * 查询列表
     */
    List<LabelResultVo> query(long dataRowId, long taskId);

    /**
     * 新增
     */
    List<LabelResultVo> insert(List<LabelIndexVo> labelIndexVos);

    void delete(List<Long> ids);
}
