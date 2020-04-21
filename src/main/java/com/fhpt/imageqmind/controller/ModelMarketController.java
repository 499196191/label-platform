package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.ModelMarketVo;
import com.fhpt.imageqmind.service.ModelMarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 模型相关接口
 */
@Api(value = "模型市场相关接口")
@RestController
@RequestMapping("/modelMarket")
public class ModelMarketController {

    @Autowired
    private ModelMarketService modelMarketService;

    @ApiOperation(value = "新增模型市场", notes = "")
    @PostMapping("/insert")
    public Result add(@RequestBody ModelMarketVo modelMarketVo){
        Result<Boolean> result = new Result<>();
        try {
            modelMarketService.insert(modelMarketVo) ;
            result.code = 0;
            result.msg = "新增成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "新增失败：保存异常";
        }
        return result;
    }

    @ApiOperation(value = "列表查询")
    @ApiOperationSort(3)
    @GetMapping("/query")
    public Result<PageInfo<ModelMarketVo>> query(@RequestParam(value = "deployStatus", defaultValue = "0", required = false) Integer deployStatus,
                                               @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                               @RequestParam(value = "page", defaultValue = "10", required = false) Integer pageSize){
        Result<PageInfo<ModelMarketVo>> result = new Result<>();
        result.data = modelMarketService.query(deployStatus, page, pageSize);
        result.code = 0;
        result.msg = "查询成功";
        return result;
    }
    @ApiOperation(value = "更新模型部署信息", notes = "")
    @ApiOperationSort(5)
    @PostMapping("/update")
    public Result<ModelMarketVo> update(@RequestBody ModelMarketVo modelMarketVo) {
        Result<ModelMarketVo> result = new Result<>();
        try {
            modelMarketService.update(modelMarketVo);
            result.code = 0;
            result.msg = "更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "更新异常";
        }
        return result;
    }
    @ApiOperation(value = "删除")
    @ApiOperationSort(4)
    @GetMapping("/delete")
    public Result delete(@RequestParam(value = "id") long id){
        Result result = new Result<>();
        try {
            modelMarketService.delete(id);
            result.code = 0;
            result.msg = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "删除失败：未找到数据";
        }
        return result;
    }

}
