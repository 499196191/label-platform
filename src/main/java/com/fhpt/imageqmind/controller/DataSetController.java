package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.DataSetVo;
import com.fhpt.imageqmind.service.DataSetService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据集相关接口
 * @author Marty
 */
@Api(value = "数据集相关接口", description = "基于数据集的操作")
@ApiSort(1)
@RestController
@RequestMapping("/dataSet")
public class DataSetController {

    @Autowired
    private DataSetService dataSetService;

    @ApiOperation(value = "查询所有")
    @ApiOperationSort(4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码(从1开始)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", dataType = "int", paramType = "query")
    })
    @GetMapping("/list")
    public Result<PageInfo<DataSetVo>> list(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                            @RequestParam(value = "page", defaultValue = "10", required = false) Integer pageSize) {
        Result<PageInfo<DataSetVo>> result = new Result<>();
        result.code = 0;
        result.data = dataSetService.query(page, pageSize);
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "新增数据集", notes = "用户可以通过此接口新增单个的数据集，注意：数据集的数据源可能有oracle、excel、csv几种格式")
    @ApiOperationSort(1)
    @PostMapping("/add")
    public Result<DataSetVo> add(@RequestBody DataSetVo dataSet) {
        Result<DataSetVo> result = new Result<>();
        result.data = dataSetService.insert(dataSet);
        if (result.data.getId() != 0) {
            result.code = 0;
            result.msg = "新增成功";
        } else {
            result.code = -1;
            result.msg = "新增失败";
        }
        return result;
    }

    @ApiOperation(value = "更新数据集", notes = "用户可以通过此接口修改单个的数据集，注意：数据集的数据源可能有oracle、excel、csv几种格式")
    @ApiOperationSort(2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "数据集名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ip", value = "数据库ip", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "port", value = "数据库端口", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "数据库用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "数据库密码", dataType = "String", paramType = "query")
    })
    @PostMapping("/update")
    public Result<Boolean> update(Long id, String name, String ip, Integer port, String username, String password) {
        Result<Boolean> result = new Result<>();
        result.data = dataSetService.update(id, name, ip, port, username, password);
        if (result.data) {
            result.code = 0;
            result.msg = "更新成功";
        } else {
            result.code = -1;
            result.msg = "更新失败";
        }
        return result;
    }

    @ApiOperation(value = "删除数据集")
    @ApiOperationSort(3)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query")})
    @GetMapping("/delete")
    public Result delete(Long id) {
        Result result = new Result<>();
        if (dataSetService.delete(id)) {
            result.code = 0;
            result.msg = "删除成功";
        } else {
            result.code = -1;
            result.msg = "删除失败";
        }
        return result;
    }

    @ApiOperation(value = "连接数据集，异构数据至中台")
    @ApiOperationSort(4)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query")})
    @PostMapping("/connect")
    public Result connectDb(Long id){
        return null;
    }
}
