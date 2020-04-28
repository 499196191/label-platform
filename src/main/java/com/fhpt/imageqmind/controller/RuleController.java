package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.Result;


import com.fhpt.imageqmind.objects.vo.AddRuleVo;

import com.fhpt.imageqmind.objects.vo.RuleInfoVo;
import com.fhpt.imageqmind.objects.vo.RuleTagVo;


import com.fhpt.imageqmind.objects.vo.TaskInfoVo;
import com.fhpt.imageqmind.service.RuleInfoService;
import com.fhpt.imageqmind.service.RuleTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 规则模块相关接口
 * @author Marty
 */
@Api(value = "规则模块相关接口")
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleTagService ruleTagService;
    @Autowired
    private RuleInfoService ruleInfoService;

    @ApiOperation(value = "新增标签")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "标签名", dataType = "string", paramType = "form")})
    @PostMapping("/tag/add")
    public Result addTag(String name) {
        Result result = new Result<>();
        try {
            if (StringUtils.isEmpty(name)) {
                result.code = -1;
                result.msg = "新增失败：标签名称不能为空！";
            } else {
                ruleTagService.add(name);
                result.code = 0;
                result.msg = "新增成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "新增失败";
        }
        return result;
    }

    @ApiOperation(value = "验证标签是否重名")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "标签名", dataType = "string", paramType = "query")})
    @GetMapping("/tag/verify")
    public Result<Boolean> verifyName(@RequestParam("name") String name) {
        Result<Boolean> result = new Result<>();
        result.code = 1;
        result.data = ruleTagService.verifyName(name);
        result.msg = "查询完成";
        return result;
    }

    @ApiOperation(value = "修改标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标签ID", dataType = "long", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "新的标签名", dataType = "string", paramType = "form")
    })
    @PostMapping("/tag/update")
    public Result updateTag(Long id, String name){
        Result result = new Result<>();
        try {
            ruleTagService.update(id, name);
            result.code = 0;
            result.msg = "新增成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "新增失败";
        }
        return result;
    }

    @ApiOperation(value = "删除标签")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "标签ID", dataType = "Long", paramType = "form")})
    @PostMapping("/tag/delete")
    public Result deleteTag(Long id) {
        Result result = new Result<>();
        if (ruleTagService.delete(id)) {
            result.code = 0;
            result.msg = "删除成功";
        } else {
            result.code = -1;
            result.msg = "删除失败";
        }
        return result;
    }

    @ApiOperation(value = "获取所有标签列表")
    @GetMapping("/tag/list")
    public Result<List<RuleTagVo>> getTagList() {
        Result<List<RuleTagVo>> result = new Result<>();
        result.code = 0;
        result.data = ruleTagService.list();
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "新增规则")
    @PostMapping("/add")
    public Result addRule(@RequestBody AddRuleVo addRuleVo){
        Result result = new Result<>();
        try {
            ruleInfoService.add(addRuleVo);
            result.code = 0;
            result.msg = "新增成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "新增失败:数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "修改规则")
    @PostMapping("/update")
    public Result updateRule(@RequestBody AddRuleVo addRuleVo){
        Result result = new Result<>();
        try {
            ruleInfoService.update(addRuleVo);
            result.code = 0;
            result.msg = "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "修改失败:数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "删除规则")
    @ApiImplicitParams({@ApiImplicitParam(name = "ruleIds", value = "多个规则ID", dataType = "String", paramType = "form")})
    @PostMapping("/delete")
    public Result delete(String ruleIds){
        Result result = new Result<>();
        try {
            ruleInfoService.delete(ruleIds);
            result.code = 0;
            result.msg = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "删除失败:数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "分页查询规则列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码(从1开始)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小(默认10)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "ruleTagIds", value = "规则标签IDs", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "typeId", value = "规则类型Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "规则名称", dataType = "String", paramType = "query")
    })
    @GetMapping("/list")
    public Result<PageInfo<RuleInfoVo>> query(@RequestParam(value = "ruleTagIds", defaultValue = "", required = false) String ruleTagIds,
                                              @RequestParam(value = "typeId", defaultValue = "-1", required = false) Integer typeId,
                                              @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                              @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        Result<PageInfo<RuleInfoVo>> result = new Result<>();
        result.code = 0;
        result.data = ruleInfoService.query(ruleTagIds, typeId, name, page, pageSize);
        result.msg = "查询成功！";
        return result;
    }

    @ApiOperation(value = "查询最近使用的词列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "word", value = "搜索词", dataType = "String", paramType = "query")})
    @GetMapping("/recentWords/get")
    public Result<List<String>> getRecentWords(@RequestParam(value = "word", defaultValue = "", required = false) String word) {
        Result<List<String>> result = new Result<>();
        try {
            result.code = 0;
            result.data = ruleInfoService.getRecentWords(word);
            result.msg = "查询成功！";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "查询失败：程序异常！";
        }
        return result;
    }
}
