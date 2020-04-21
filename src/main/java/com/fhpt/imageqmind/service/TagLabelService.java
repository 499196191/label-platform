package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.exceptions.VerifyException;
import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.vo.TagLabelCount;

import com.fhpt.imageqmind.objects.vo.TagLabelVo;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

/**
 * 标签业务接口
 * @author Marty
 */
public interface TagLabelService {

    boolean checkName(TagLabelVo tagLabel);

    TagLabelVo add(TagLabelVo tagLabel);

    boolean verify(Integer type, String name, boolean isChinese) throws VerifyException;

    TagLabelCount getCountInfo();

    PageInfo<TagLabelVo> query(Integer type, Integer page, Integer pageSize);

    boolean delete(String ids);

    boolean update(TagLabelVo tagLabel);

    void changeType(List<Long> ids, int type);

    boolean isValid(String chineseName, String englishName);
}
