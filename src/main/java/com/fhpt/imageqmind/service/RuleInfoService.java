package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.AddRuleVo;
import com.fhpt.imageqmind.objects.vo.RuleInfoVo;

import java.util.List;

/**
 * 规则信息业务
 * @author Marty
 */
public interface RuleInfoService {

    void add(AddRuleVo addRuleVo);

    boolean update(AddRuleVo addRuleVo);

    void delete(Long ruleId);

    PageInfo<RuleInfoVo> query(String ruleTagIds, int typeId, String name, Integer page, Integer pageSize);

    List<String> getRecentWords(String word);
}
