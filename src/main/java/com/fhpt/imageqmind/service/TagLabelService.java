package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.vo.TagLabelCount;

import com.fhpt.imageqmind.objects.vo.TagLabelVo;



import java.util.List;

/**
 * 标签业务接口
 * @author Marty
 */
public interface TagLabelService {

    boolean checkName(TagLabelVo tagLabel);

    TagLabelVo add(TagLabelVo tagLabel);

    TagLabelCount getCountInfo();

    PageInfo<TagLabelVo> query(Integer type, Integer page, Integer pageSize);

    void delete(Long id);

    boolean update(TagLabelVo tagLabel);

    void changeType(List<Long> ids, int type);
}
