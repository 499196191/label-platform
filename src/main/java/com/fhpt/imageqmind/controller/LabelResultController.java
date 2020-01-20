package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.LabelIndexVo;
import com.fhpt.imageqmind.objects.vo.LabelResultVo;
import com.fhpt.imageqmind.service.LabelResultService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标注核心相关接口
 * @author Marty
 */
@Api(value = "标注相关接口", description = "基于一行数据文本的标注操作")
@ApiSort(2)
@RestController
@RequestMapping("/labelResult")
public class LabelResultController {

    @Autowired
    private LabelResultService labelResultService;

    @ApiOperation(value = "查询所有")
    @ApiOperationSort(1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataRowId", value = "数据行ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "query")
    })
    @GetMapping("/list")
    public Result<List<LabelResultVo>> query(@RequestParam(value = "dataRowId", required = true)Long dataRowId,
                                             @RequestParam(value = "taskId", required = true)Long taskId){
        Result<List<LabelResultVo>> result = new Result<>();
        result.code = 0;
        result.data = labelResultService.query(dataRowId, taskId);
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "新增文本标注", notes = "用户可以通过此接口批量新增")
    @ApiOperationSort(2)
    @PostMapping("/add")
    public Result<List<LabelResultVo>> add(@RequestBody List<LabelIndexVo> labelIndexs){
        Result<List<LabelResultVo>> result = new Result<>();
        result.code = 0;
        result.data = labelResultService.insert(labelIndexs);
        result.msg = "新建成功";
        return result;
    }

    @ApiOperation(value = "删除文本标注", notes = "支持批量删除")
    @ApiOperationSort(3)
    @GetMapping("/delete")
    public Result delete(@RequestBody List<Long> ids) {
        Result result = new Result<>();
        try {
            labelResultService.delete(ids);
            result.code = 0;
            result.msg = "删除成功";
        } catch (Exception e) {
            result.code = -1;
            result.msg = "删除失败";
        }
        return result;
    }
}
