package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.DataRowVo;
import com.fhpt.imageqmind.service.DataRowService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据行信息
 * @author Marty
 */
@Api(value = "数据行相关接口", description = "基于数据行相关的操作接口")
@ApiSort(4)
@RestController
@RequestMapping("/dataRow")
public class DataRowController {

    @Autowired
    private DataRowService dataRowService;

    @ApiOperation(value = "查看数据行详情")
    @ApiOperationSort(1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataRowId", value = "数据行ID", dataType = "long", paramType = "query")
    })
    @GetMapping("/detail")
    public Result<DataRowVo> detail(@RequestParam(value = "dataRowId", required = true) Long dataRowId) {
        Result<DataRowVo> result = new Result<>();
        result.code = 0;
        result.data = dataRowService.detail(dataRowId);
        result.msg = "查询成功！";
        return result;
    }
}
