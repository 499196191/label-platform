package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.AddTrainingVo;
import com.fhpt.imageqmind.objects.vo.TrainingDetailVo;
import com.fhpt.imageqmind.objects.vo.TrainingInfoVo;
import com.fhpt.imageqmind.service.TrainingInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 模型训练任务相关接口
 * @author Marty
 */
@Api(value = "模型训练任务相关接口", description = "基于模型训练任务的操作接口")
@RestController
@RequestMapping("/training")
public class TrainingInfoController {

    @Autowired
    private TrainingInfoService trainingInfoService;

    @ApiOperation(value = "仅保存训练任务")
    @PostMapping("/save")
    public Result add(@RequestBody AddTrainingVo addTrainingVo){
        Result result = new Result();
        try {
            trainingInfoService.save(addTrainingVo);
            result.code = 0;
            result.msg = "保存成功！";
        }catch (Exception e){
            e.printStackTrace();
            result.code = -1;
            result.msg = "保存失败！";
        }
        return result;
    }

    @ApiOperation(value = "开始训练任务")
    @PostMapping("/saveAndStart")
    public Result saveAndStart(@RequestBody AddTrainingVo addTrainingVo){
        Result result = new Result();
        try {
            trainingInfoService.saveAndStart(addTrainingVo);
            result.code = 0;
            result.msg = "开始训练任务成功！";
        }catch (Exception e){
            e.printStackTrace();
            result.code = -1;
            result.msg = "开始训练任务失败！";
        }
        return result;
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码(从1开始)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小(默认10)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "训练类型", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "trainingStatus", value = "训练状态", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "tags", value = "训练标签", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "训练任务名称", dataType = "String", paramType = "query")
    })
    @GetMapping("/list")
    public Result<PageInfo<TrainingInfoVo>> query(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                        @RequestParam(value = "type", defaultValue = "1", required = false) Integer type,
                        @RequestParam(value = "trainingStatus", defaultValue = "-1", required = false) Integer trainingStatus,
                        @RequestParam(value = "tags", defaultValue = "", required = false) String tags,
                        @RequestParam(value = "name", defaultValue = "", required = false) String name){

        Result<PageInfo<TrainingInfoVo>> result = new Result<>();
        result.code = 0;
        result.data = trainingInfoService.query(page, pageSize, type, trainingStatus, tags, name);
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "删除训练任务")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query")})
    @GetMapping("/delete")
    public Result delete(Long id) {
        Result result = new Result();
        try {
            if (trainingInfoService.delete(id)) {
                result.code = 0;
                result.msg = "删除成功";
            } else {
                result.code = -1;
                result.msg = String.format("删除失败:未找到训练任务 [id] 为 [%d] 的数据", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "删除失败:数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "获取训练任务详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query")})
    @GetMapping("/detail")
    public Result<TrainingDetailVo> detail(Long id) {
        Result<TrainingDetailVo> result = new Result<>();
        result.code = 0;
        result.data = trainingInfoService.get(id);
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "暂停训练任务")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query")})
    @GetMapping("/suspend")
    public Result<Boolean> suspend(Long id){
        Result<Boolean> result = new Result<>();
        result.code = 0;
        result.data = trainingInfoService.suspend(id);
        result.msg = "执行暂停训练";
        return result;
    }

    @ApiOperation(value = "重新开启训练任务")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query")})
    @GetMapping("/restart")
    public Result<Boolean> restart(Long id){
        Result<Boolean> result = new Result<>();
        result.code = 0;
        result.data = trainingInfoService.restart(id);
        result.msg = "执行重新开启训练";
        return result;
    }
}
