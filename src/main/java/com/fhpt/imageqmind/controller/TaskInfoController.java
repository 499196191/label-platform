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
    @ApiImplicitParams({@ApiImplicitParam(name = "typeId", value = "任务类型", dataType = "long", paramType = "query", required = false),
            @ApiImplicitParam(name = "status", value = "任务状态", dataType = "long", paramType = "query", required = false),
            @ApiImplicitParam(name = "tag", value = "分类标签（多个标签以id逗号拼接）", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "name", value = "任务名称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "sizeIsNotZero", value = "任务完成量是否为0", dataType = "boolean", paramType = "query", required = false),
            @ApiImplicitParam(name = "page", value = "页码(从1开始)", dataType = "int", paramType = "query",required = false),
            @ApiImplicitParam(name = "pageSize", value = "页大小(默认10)", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/list")
    public Result<PageInfo<TaskInfoVo>> query(@RequestParam(value = "typeId", defaultValue = "-1", required = false) Long typeId,
                                              @RequestParam(value = "status", defaultValue = "-1", required = false)Integer status,
                                              @RequestParam(value = "tag", defaultValue = "", required = false)String tag,
                                              @RequestParam(value = "name", defaultValue = "", required = false)String name,
                                              @RequestParam(value = "sizeIsNotZero", defaultValue = "false", required = false)boolean sizeIsNotZero,
                                              @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        Result<PageInfo<TaskInfoVo>> result = new Result<>();
        result.code = 0;
        result.data = taskInfoService.query(page, pageSize, typeId, status, tag, name, sizeIsNotZero);
        result.msg = "查询成功！";
        return result;
    }

    @ApiOperation(value = "新增任务")
    @ApiOperationSort(3)
    @PostMapping("/add")
    public Result add(@RequestBody AddTaskVo addTaskVo) {
        Result<Long> result = new Result<>();
        try {
            result.data = taskInfoService.add(addTaskVo);
            if (result.data > 0) {
                result.code = 0;
                result.msg = "新增任务成功！";
            } else {
                result.code = -1;
                result.msg = "新增任务失败！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "新增任务失败！";
        }
        return result;
    }

    @ApiOperation(value = "删除任务")
    @PostMapping("/delete")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "form", required = true)})
    public Result delete(Long taskId) {
        Result result = new Result<>();
        try {
            if (taskInfoService.delete(taskId)) {
                result.code = 0;
                result.msg = "删除成功";
            } else {
                result.code = -1;
                result.msg = String.format("删除失败:未找到标注任务 [id] 为 [%d] 的数据", taskId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "删除失败：数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "停止任务")
    @PostMapping("/stop")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "form", required = false),})
    public Result stop(Long taskId) {
        Result result = new Result<>();
        if (taskInfoService.stop(taskId)) {
            result.code = 0;
            result.msg = "停止成功";
        } else {
            result.code = -1;
            result.msg = "停止失败：未找到标注任务数据";
        }
        return result;
    }

    @ApiOperation(value = "继续任务")
    @PostMapping("/restart")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "form", required = false),})
    public Result restart(Long taskId) {
        Result result = new Result<>();
        if (taskInfoService.restart(taskId)) {
            result.code = 0;
            result.msg = "重启成功";
        } else {
            result.code = -1;
            result.msg = "重启失败：未找到标注任务数据";
        }
        return result;
    }
}
