package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.domain.TagDataRowEntity;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.AddTaskVo;
import com.fhpt.imageqmind.objects.vo.TaskInfoVo;

/**
 * 数据文本分类标注
 * @author Marty
 */
public interface TagDataRowService {

    void add(TagDataRowEntity tagDataRowEntity);
}
