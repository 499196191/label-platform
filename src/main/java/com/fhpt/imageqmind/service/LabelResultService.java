package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.domain.LabelResultEntity;

import com.fhpt.imageqmind.objects.vo.LabelIndexVo;

import com.fhpt.imageqmind.objects.vo.LabelResultDetail;
import com.fhpt.imageqmind.objects.vo.LabelResultVo;


import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据行标注业务类
 */
public interface LabelResultService {
    /**
     * 查询列表
     */
    LabelResultDetail query(long dataRowId, long taskId);

    /**
     * 新增
     */
    boolean insert(List<LabelIndexVo> labelIndexVos);

    void delete(List<Long> ids);

    boolean isCrossAndHandelDuplicate(List<LabelIndexVo> labelIndexVos, List<LabelIndexVo> handelResult);

    /**
     * 解析标注结果，标注允许嵌套或重叠，但不允许交叉
     * @return 标注列表
     */
    List<LabelResultEntity> parseLabelResult(long taskId, long rowId, String annotatedDoc);

    void initLabelResult(List<LabelResultEntity> list, long taskId, long rowId);
}
