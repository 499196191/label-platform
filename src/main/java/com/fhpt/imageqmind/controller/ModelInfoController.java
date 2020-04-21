package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.domain.ModelInfoEntity;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.*;
import com.fhpt.imageqmind.service.DataSetService;
import com.fhpt.imageqmind.service.ModelInfoService;
import com.fhpt.imageqmind.service.TrainingInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型相关接口
 */
@Api(value = "模型管理相关接口",  description = "模型管理相关接口")
@RestController
@RequestMapping("/modelInfo")
public class ModelInfoController {

    @Autowired
    private ModelInfoService modelInfoService;
    @Autowired
    private DataSetService dataSetService;
    @Autowired
    private TrainingInfoService trainingInfoService;

    @ApiOperation(value = "新增模型", notes = "用户可以通过此接口保存模型")
    @PostMapping("/add")
    public Result add(@RequestBody AddModelVo addModelVo) {
        Result<Boolean> result = new Result<>();
        try {
            if (modelInfoService.add(addModelVo)) {
                result.code = 0;
                result.msg = "新增成功";
            } else {
                result.code = -1;
                result.msg = "新增失败：训练任务id未找到对应信息";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "新增失败：保存异常";
        }
        return result;
    }

    @ApiOperation(value = "新增规则模型", notes = "用户可以通过此接口保存规则模型")
    @PostMapping("/rule/add")
    public Result addRuleModel(@RequestBody AddRuleModelVo addRuleModelVo) {
        Result<Boolean> result = new Result<>();
        try {
            if (modelInfoService.add(addRuleModelVo)) {
                result.code = 0;
                result.msg = "新增成功";
            } else {
                result.code = -1;
                result.msg = "新增失败：规则模型中必须得有规则！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "新增失败：保存异常";
        }
        return result;
    }

    @ApiOperation(value = "模型信息查询", notes = "模型信息查询")
    @ApiOperationSort(3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelName", value = "模型名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "modelType", value = "模型类型(1:实体识别, 2:文本分类 3:关系抽取)", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "typesName", value = "分类标签", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deployStatus", value = "部署状态(0: 待部署，1：待发布，2：已发布)", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sourceType", value = "模型来源(1, 模型训练 2, 规则配置 3, 外部导入 )", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", dataType = "Integer", paramType = "query")
    })
    @PostMapping("/query")
    public Result<PageInfo<ModelInfoVo>> query(@RequestParam(value = "modelName", required = false) String modelName,
                                               @RequestParam(value = "sourceType", defaultValue = "", required = false) Integer sourceType,
                                               @RequestParam(value = "modelType", defaultValue = "", required = false) Integer modelType,
                                               @RequestParam(value = "typesName", defaultValue = "",required = false) String typesName,
                                               @RequestParam(value = "deployStatus", defaultValue = "", required = false) Integer deployStatus,
                                               @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        Result<PageInfo<ModelInfoVo>> result = new Result<>();
        result.data = modelInfoService.query(modelName, modelType,sourceType,typesName, deployStatus, page, pageSize);
        result.code = 0;
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "模型信息更新", notes = "模型信息更新")
    @ApiOperationSort(4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模型id", dataType = "long", paramType = "update"),
            @ApiImplicitParam(name = "modelName", value = "模型名称", dataType = "String", paramType = "update"),
            @ApiImplicitParam(name = "typesName", value = "分类标签(以,号拼接起来的字符串)", dataType = "String", paramType = "update"),
            @ApiImplicitParam(name = "deployStatus", value = "部署状态(0: 待部署，1：待发布，2：已发布)", dataType = "Integer", paramType = "update"),
            @ApiImplicitParam(name = "version", value = "模型版本", dataType = "String", paramType = "update"),
            @ApiImplicitParam(name = "brief", value = "模型简介", dataType = "String", paramType = "update")
    })
    @PostMapping("/update")
    public Result<ModelInfoVo> update(@RequestParam(value = "id", defaultValue = "", required = false) long id,
                                      @RequestParam(value = "modelName", required = false) String modelName,
                                      @RequestParam(value = "typesName", required = false) String typesName,
                                      @RequestParam(value = "version", defaultValue = "", required = false) String version,
                                      @RequestParam(value = "brief", defaultValue = "", required = false) String brief,
                                      @RequestParam(value = "deployStatus", defaultValue = "", required = false) Integer deployStatus) {
        Result<ModelInfoVo> result = new Result<>();
        String log="更新";
        if(deployStatus==1){
            log="部署";
        }else if(deployStatus==2){
            log="发布";
        }
        if (modelInfoService.update(id, deployStatus,modelName,typesName,version,brief)) {
            result.code = 0;
            result.msg = log+"成功";
        } else {
            result.code = -1;
            result.msg = log+"失败";
        }
        return result;
    }

    @ApiOperation(value = "模型撤销部署", notes = "模型撤销部署")
    @ApiOperationSort(5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模型id", dataType = "long", paramType = "query")
    })
    @GetMapping("/delete")
    public Result delete(@RequestParam(value = "id") long id) {
        Result result = new Result<>();
        try {
            boolean flag=modelInfoService.delete(id);
            result.code =flag? 0:-1;
            result.msg = flag? "撤销部署成功": "撤销部署失败";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "撤销部署失败";
        }
        return result;
    }
    @ApiOperation(value = "模型删除", notes = "模型信息删除")
    @ApiOperationSort(5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模型id", dataType = "long", paramType = "query")
    })
    @GetMapping("/del")
    public Result del(@RequestParam(value = "id") long id) {
        Result result = new Result<>();
        try {
            modelInfoService.del(id);
            result.code = 0;
            result.msg = "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "删除失败：未找到数据";
        }
        return result;
    }

    @ApiOperation(value = "查看", notes = "模型信息查看")
    @ApiOperationSort(6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模型id", dataType = "long", paramType = "query")
    })
    @GetMapping("/view")
    public Result view(@RequestParam(value = "id") long id) {
        Result result = new Result<>();
        result.code = 0;
        result.msg = "查询成功";
        Map<String ,Object> map =new HashMap<>();
        ModelInfoEntity modelInfoEntity =modelInfoService.view(id);
        ModelInfoVo modelInfoVo =getModelInfoVo(modelInfoEntity);
        map.put("modelInfo",modelInfoVo);
        //待部署状态不展示api接口信息
        if(modelInfoEntity.getDeployStatus()!=0){
            map.put("apiInfo",getApiInfoVo(modelInfoEntity));
        }else {
            map.put("apiInfo",null);
        }
        if(1==modelInfoEntity.getSourceType()){
            TrainingDetailVo trainingDetailVo= trainingInfoService.get(modelInfoVo.getTrainingId());
            List<TrainingTag> tagList =trainingDetailVo.getTrainingTags();
            StringBuffer sb=new StringBuffer();
            tagList.forEach(tag ->{
                sb.append(tag.getTagName()).append(",");

            });
            trainingDetailVo.setTrainingTagNames(sb.toString());
            map.put("trainingDetailVo",trainingDetailVo);
        }else{
            map.put("trainingDetailVo",null);
        }

        result.data = map;
        return result;
    }

    private ModelInfoVo getModelInfoVo(ModelInfoEntity modelInfoEntity) {
        ModelInfoVo modelInfoVo = new ModelInfoVo();
        BeanUtils.copyProperties(modelInfoEntity, modelInfoVo);
        if(1==modelInfoEntity.getSourceType()){
            modelInfoVo.setTrainingId(modelInfoEntity.getTrainingInfo().getId());
        }
        modelInfoVo.setCreateTime(modelInfoEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        modelInfoVo.setUpdateTime(modelInfoEntity.getUpdateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return modelInfoVo;
    }
    private ApiInfoVo getApiInfoVo(ModelInfoEntity modelInfoEntity) {
        ApiInfoVo apiInfoVo = new ApiInfoVo();
        if(modelInfoEntity.getApiInfoEntity()==null){
            return null;
        }
        BeanUtils.copyProperties(modelInfoEntity.getApiInfoEntity(), apiInfoVo);
        return apiInfoVo;
    }
}
