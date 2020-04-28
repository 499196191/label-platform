package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.vo.RuleTagVo;



import java.util.List;

/**
 * 规则标签业务
 * @author Marty
 */
public interface RuleTagService {

    void add(String name);

    boolean verifyName(String name);

    boolean update(Long id, String name);

    boolean delete(Long id);

    List<RuleTagVo> list();
}
