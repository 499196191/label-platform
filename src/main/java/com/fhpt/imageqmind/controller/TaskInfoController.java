package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.Result;

import com.fhpt.imageqmind.objects.vo.AddTaskVo;
import com.fhpt.imageqmind.objects.vo.TaskInfoVo;
import com.fhpt.imageqmind.service.TaskInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * 任务信息
 * @author Marty
 */
@Api(value = "标注任务相关接口", description = "基于标注相关的操作接口")
@ApiSort(3)
@RestController
@RequestMapping("/taskInfo")
public class TaskInfoController {

    @Autowired
    private TaskInfoService taskInfoService;

    @ApiOperation(value = "查看任务详情")
    @ApiOperationSort(1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "query")
    })
    @GetMapping("/detail")
    public Result<TaskInfoVo> detail(@RequestParam(value = "taskId", required = true)Long taskId){
        Result<TaskInfoVo> result = new Result<>();
        result.code = 0;
        result.data = taskInfoService.detail(taskId);
        result.msg = "查询成功！";
        return result;
    }

    @ApiOperation(value = "分页查询")
    @ApiOperationSort(2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码(从1开始)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小(默认10)", dataType = "int", paramType = "query")
    })
    @GetMapping("/list")
    public Result<PageInfo<TaskInfoVo>> query(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        Result<PageInfo<TaskInfoVo>> result = new Result<>();
        result.code = 0;
        result.data = taskInfoService.query(page, pageSize);
        result.msg = "查询成功！";
        return result;
    }

    @ApiOperation(value = "新增任务")
    @ApiOperationSort(3)
    @PostMapping("/add")
    public Result add(@RequestBody AddTaskVo addTaskVo){
        taskInfoService.add(addTaskVo);
        return null;
    }


}
