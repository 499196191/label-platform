package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.PageInfo;


import com.fhpt.imageqmind.objects.vo.AddTaskVo;
import com.fhpt.imageqmind.objects.vo.TaskInfoVo;

/**
 * 任务信息业务
 * @author Marty
 */
public interface TaskInfoService {

    TaskInfoVo detail(Long taskId);

    PageInfo<TaskInfoVo> query(Integer page, Integer pageSize);

    TaskInfoVo add(AddTaskVo addTaskVo);
}
