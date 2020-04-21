package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.constant.RowStatus;

import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.DataRowVo;
import com.fhpt.imageqmind.objects.vo.DataSetDetail;
import com.fhpt.imageqmind.objects.vo.DataSetVo;
import com.fhpt.imageqmind.service.DataRowService;
import io.swagger.annotations.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * 数据行信息
 * @author Marty
 */
@Slf4j
@Api(value = "数据行相关接口", description = "基于数据行相关的操作接口")
@RestController
@RequestMapping("/dataRow")
public class DataRowController {

    @Autowired
    private DataRowService dataRowService;

    @ApiOperation(value = "查看数据行详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataRowId", value = "数据行ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "query")
    })
    @GetMapping("/detail")
    public Result<DataRowVo> detail(@RequestParam(value = "dataRowId", required = true) Long dataRowId,
                                    @RequestParam(value = "taskId", required = true) Long taskId) {
        Result<DataRowVo> result = new Result<>();
        DataRowVo dataRowVo = dataRowService.detail(dataRowId, taskId);
        if (dataRowVo != null) {
            result.code = 0;
            result.data = dataRowVo;
            result.msg = "查询成功！";
        } else {
            result.code = -1;
            result.msg = "该任务中没有此数据行记录！";
            log.info("[dataRowId]:[{}] [taskId]:[{}]该任务中没有此数据行记录！", dataRowId, taskId);
        }
        return result;
    }

    @ApiOperation(value = "查看数据集下的数据行信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataSetId", value = "数据集ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码（从1开始）", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", dataType = "int", paramType = "query")

    })
    @GetMapping("/list")
    public Result<DataSetDetail> list(@RequestParam(value = "dataSetId", required = true) Long dataSetId,
                              @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        Result<DataSetDetail> result = new Result<>();
        result.data = dataRowService.query(dataSetId, page, pageSize);
        result.code = 0;
        result.msg = "查询成功！";
        return result;
    }

    @ApiOperation(value = "标记文章为已完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataRowId", value = "数据行ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "isFinished", value = "是否标记为已完成", dataType = "boolean", paramType = "query")
    })
    @PostMapping("/finish")
    public Result<Long> setRowFinished(@RequestParam(value = "dataRowId", required = true) Long dataRowId,
                                       @RequestParam(value = "taskId", required = true) Long taskId,
                                       @RequestParam(value = "isFinished", required = true) Boolean isFinished){
        Result<Long> result = new Result<>();
        try {
            if (isFinished) {
                result.data = dataRowService.updateDataRowMapStatus(dataRowId, taskId, RowStatus.LABELED);
                result.msg = "标记成功！";
            } else {
                result.data = dataRowService.getNext(taskId);
                result.msg = "当前文章未有标记信息，暂时跳过这一篇！";
            }
            result.code = 0;
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "标记失败！";
            log.error("[dataRowId]:[{}]标记失败！", dataRowId);
        }
        return result;
    }

    @ApiOperation(value = "标记文章为标注中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataRowId", value = "数据行ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "query")
    })
    @PostMapping("/labeling")
    public Result setRowLabeling(@RequestParam(value = "dataRowId", required = true) Long dataRowId,
                                 @RequestParam(value = "taskId", required = true) Long taskId){
        Result<Long> result = new Result<>();
        try {
            result.data = dataRowService.updateDataRowMapStatus(dataRowId, taskId, RowStatus.LABELING);
            result.code = 0;
            result.msg = "标记成功！";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "标记失败！";
            log.error("[dataRowId]:[{}]标记失败！", dataRowId);
        }
        return result;
    }

    @ApiOperation(value = "标记文章为忽略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataRowId", value = "数据行ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务ID", dataType = "long", paramType = "query")
    })
    @PostMapping("/ignore")
    public Result setRowIgnored(@RequestParam(value = "dataRowId", required = true) Long dataRowId,
                                @RequestParam(value = "taskId", required = true) Long taskId){
        Result<Long> result = new Result<>();
        try {
            result.data = dataRowService.updateDataRowMapStatus(dataRowId, taskId, RowStatus.IGNORED);
            result.code = 0;
            result.msg = "标记成功！";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "标记失败！";
            log.error("[dataRowId]:[{}]标记失败！", dataRowId);
        }
        return result;
    }
}
