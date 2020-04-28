package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.annotation.EagleEye;

import com.fhpt.imageqmind.constant.method.Add;
import com.fhpt.imageqmind.constant.method.Update;
import com.fhpt.imageqmind.exceptions.TagNameVerifyException;
import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.Result;


import com.fhpt.imageqmind.objects.vo.TagLabelCount;
import com.fhpt.imageqmind.objects.vo.TagLabelVo;

import com.fhpt.imageqmind.service.TagLabelService;

import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
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
@Validated
@RestController
@RequestMapping("/tagLabel")
public class TagLabelController {

    @Autowired
    private TagLabelService tagLabelService;

    @ApiOperation(value = "新增标签", notes = "用户可以通过此接口新增单个标签")
    @ApiOperationSort(1)
    @PostMapping("/add")
    public Result<TagLabelVo> add(@RequestBody @Validated(Add.class) TagLabelVo tagLabel) {
        Result<TagLabelVo> result = new Result<>();
        if (!tagLabelService.isValid(tagLabel.getName(), tagLabel.getEnglishName())) {
            result.code = -1;
            result.msg = "标签中文名和英文名不能包含空格，并且不能以数字开头";
        }
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

    @ApiOperation(value = "验证标签名称是否可用", notes = "用户可以及时验证标签是否在当前分组存在重名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", dataType = "integer", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "isChinese", value = "是否是中文", dataType = "boolean", paramType = "query", required = true)
    })
    @GetMapping("/verify")
    public Result<Boolean> verify(@RequestParam(value = "type", required = true) Integer type,
                                  @RequestParam(value = "name", required = true) String name,
                                  @RequestParam(value = "isChinese", required = true) Boolean isChinese) {

        Result<Boolean> result = new Result<>();
        try {
            result.data = tagLabelService.verify(type, name, isChinese);
            result.msg = "验证成功";
            result.code = 0;
        } catch (TagNameVerifyException e) {
            result.msg = e.getMessage();
            result.data = false;
            result.code = 0;
        } catch (Exception e) {
            result.code = -1;
            result.msg = "验证失败";
            result.data = false;
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
    @EagleEye
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "isDelete", value = "是否是回收站内容", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", dataType = "integer", paramType = "query")
    })
    public Result<PageInfo<TagLabelVo>> query(@RequestParam(value = "type", defaultValue = "-1", required = false) Integer type,
                                              @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                              @RequestParam(value = "isDelete", defaultValue = "false", required = false) boolean isDelete,
                                              @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        Result<PageInfo<TagLabelVo>> result = new Result<>();
        result.data = tagLabelService.query(type, name, page, pageSize, isDelete);
        result.code = 0;
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "标签删除(伪删除：放入回收站)")
    @ApiOperationSort(4)
    @GetMapping("/delete")
    public Result delete(@RequestParam(value = "id") String ids) {
        Result result = new Result<>();
        try {
            if (tagLabelService.delete(ids)) {
                result.code = 0;
                result.msg = "删除成功";
            } else {
                result.code = -1;
                result.msg = "删除失败：传递的[ids]参数为空";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "删除失败：数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "标签还原")
    @GetMapping("/restore")
    public Result restore(@RequestParam(value = "id") Long id) {
        Result result = new Result<>();
        try {
            if (tagLabelService.restore(id)) {
                result.code = 0;
                result.msg = "还原成功";
            } else {
                result.code = -1;
                result.msg = "还原失败：传递的[id]参数为空";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "还原失败：数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "标签彻底删除(真删除：相关的标注信息也都删除)")
    @GetMapping("/delete/force")
    public Result deleteForce(@RequestParam(value = "id") String ids) {
        Result result = new Result<>();
        try {
            if (tagLabelService.deleteForce(ids)) {
                result.code = 0;
                result.msg = "彻底删除成功";
            } else {
                result.code = -1;
                result.msg = "彻底删除失败：传递的[ids]参数为空";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "彻底删除失败：数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "更新标签", notes = "用户可以通过此接口修改单个标签")
    @ApiOperationSort(5)
    @PostMapping("/update")
    public Result<TagLabelVo> update(@RequestBody @Validated(Update.class) TagLabelVo tagLabel) {
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
