package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.exceptions.TagNameVerifyException;
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

    boolean verify(Integer type, String name, boolean isChinese) throws TagNameVerifyException;

    TagLabelCount getCountInfo();

    PageInfo<TagLabelVo> query(Integer type, String name, Integer page, Integer pageSize, boolean isDelete);

    boolean delete(String ids);

    boolean deleteForce(String ids);

    boolean restore(Long id);

    boolean update(TagLabelVo tagLabel);

    void changeType(List<Long> ids, int type);

    boolean isValid(String chineseName, String englishName);
}
