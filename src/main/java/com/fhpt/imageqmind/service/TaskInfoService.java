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

    PageInfo<TaskInfoVo> query(Integer page, Integer pageSize, Long typeId, Integer status, String tag, String name, boolean sizeIsNotZero);

    long add(AddTaskVo addTaskVo);

    boolean delete(Long taskId);

    boolean stop(Long taskId);

    boolean restart(Long taskId);
}
