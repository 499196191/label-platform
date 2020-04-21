package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.ClassifyLabelResultVo;
import com.fhpt.imageqmind.service.ClassifyLabelResultService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类标注相关接口
 * @author xie
 */
@Api(value = "分类标注接口", description = "分类标注相关接口")
@ApiSort(2)
@RestController
@RequestMapping("/classifyLabelResult")
public class ClassifyLabelResultController {

    @Autowired
    private ClassifyLabelResultService classifyLabelResultService;


    @ApiOperation(value = "新增分类标注", notes = "新增分类标注")
    @ApiOperationSort(1)
    @PostMapping("/insert")
    public Result insert(@RequestBody ClassifyLabelResultVo classifyLabelResultVo){
        Result result = new Result<>();
        try {
            if (classifyLabelResultService.insert(classifyLabelResultVo)) {
                result.code = 0;
                result.msg = "标注成功";
            } else {
                result.code = -1;
                result.msg = "标注失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "标注失败：数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "删除分类标注", notes = "删除分类标注")
    @ApiOperationSort(2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标注id", dataType = "long", paramType = "delete")
    })
    @GetMapping ("/delete")
    public Result delete(@RequestParam(value = "id") long id) {
        Result result = new Result<>();
        try {
            classifyLabelResultService.delete(id);
            result.code = 0;
            result.msg = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "删除失败";
        }
        return result;
    }
}
