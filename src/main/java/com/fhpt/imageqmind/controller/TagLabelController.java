package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.Result;


import com.fhpt.imageqmind.objects.vo.DataSetVo;


import com.fhpt.imageqmind.objects.vo.TagLabelCount;
import com.fhpt.imageqmind.objects.vo.TagLabelVo;

import com.fhpt.imageqmind.service.TagLabelService;

import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



import java.util.Arrays;



import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签管理
 * @author Marty
 */
@Api(value = "标签相关接口", description = "基于标签的操作")
@ApiSort(5)
@RestController
@RequestMapping("/tagLabel")
public class TagLabelController {

    @Autowired
    private TagLabelService tagLabelService;

    @ApiOperation(value = "新增标签", notes = "用户可以通过此接口新增单个标签")
    @ApiOperationSort(1)
    @PostMapping("/add")
    public Result<TagLabelVo> add(@RequestBody TagLabelVo tagLabel) {
        Result<TagLabelVo> result = new Result<>();
        result.data = tagLabelService.add(tagLabel);
        if (result.data.getId() != 0) {
            result.code = 0;
            result.msg = "新增成功";
        } else {
            result.code = -1;
            result.msg = String.format("已存在中文名/英文名为{%s/%s}的标签，请重新命名！", tagLabel.getName(), tagLabel.getEnglishName());
        }
        return result;
    }

    @ApiOperation(value = "标签分类统计", notes = "获取标签不同分类的统计信息")
    @ApiOperationSort(2)
    @GetMapping("/count")
    public Result<TagLabelCount> getCountInfo(){
        Result<TagLabelCount> result = new Result<>();
        result.data = tagLabelService.getCountInfo();
        result.code = 0;
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "标签查询")
    @ApiOperationSort(3)
    @GetMapping("/query")
    public Result<PageInfo<TagLabelVo>> query(@RequestParam(value = "type", defaultValue = "0", required = false) Integer type,
                                              @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                              @RequestParam(value = "page", defaultValue = "10", required = false) Integer pageSize){
        Result<PageInfo<TagLabelVo>> result = new Result<>();
        result.data = tagLabelService.query(type, page, pageSize);
        result.code = 0;
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "标签删除")
    @ApiOperationSort(4)
    @GetMapping("/delete")
    public Result delete(@RequestParam(value = "id") Long id){
        Result result = new Result<>();
        try {
            tagLabelService.delete(id);
            result.code = 0;
            result.msg = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "删除失败：未找到数据";
        }
        return result;
    }

    @ApiOperation(value = "更新标签", notes = "用户可以通过此接口修改单个标签")
    @ApiOperationSort(5)
    @PostMapping("/update")
    public Result<TagLabelVo> update(@RequestBody TagLabelVo tagLabel) {
        Result<TagLabelVo> result = new Result<>();
        if (tagLabelService.update(tagLabel)) {
            result.code = 0;
            result.msg = "更新成功";
        } else {
            result.code = -1;
            result.msg = "更新失败";
        }
        return result;
    }

    @ApiOperation(value = "移动分组", notes = "用户可以通过此接口批量移动标签至指定分组")
    @ApiOperationSort(6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "标签类型", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "ids", value = "多个标签的id 用逗号,拼接", dataType = "String", paramType = "query")
    })
    @PostMapping("/type/change")
    public Result changeType(Integer type, String ids){
        Result result = new Result<>();
        List<Long> idList = Arrays.stream(ids.split(",")).map(id -> Long.parseLong(id)).collect(Collectors.toList());
        try {
            tagLabelService.changeType(idList, type);
            result.code = 0;
            result.msg = "分组成功";
        }catch (Exception e){
            e.printStackTrace();
            result.code = -1;
            result.msg = "分组失败！";
        }
        return result;
    }
}
