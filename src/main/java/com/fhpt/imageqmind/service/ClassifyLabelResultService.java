package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.vo.ClassifyLabelResultVo;

/**
 * 数据行标注业务类
 */
public interface ClassifyLabelResultService {

    /**
     * 新增
     */
    boolean insert(ClassifyLabelResultVo classifyLabelResultVo);

    void delete(long id);
}
