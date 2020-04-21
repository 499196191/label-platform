package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增模型传输类
 * @author Marty
 */
@ApiModel(description= "新增模型传输类")
@Data
public class AddModelVo {
    @ApiModelProperty(value = "训练任务ID", required = true)
    private long trainingId;
    @ApiModelProperty(value = "模型名称", required = true)
    private String modelName;
    @ApiModelProperty(value = "模型类型（ENTITY(1, \"实体识别\"), TYPE(2, \"文本分类\"), RELATION(3, \"关系抽取\")）", required = true)
    private int type;
    @ApiModelProperty(value = "模型来源（TRAINING(1, \"模型训练\"), RULE_SETTING(2, \"规则配置\"), OUT_IMPORT(3, \"外部导入\")）", required = true)
    private int sourceType;
    @ApiModelProperty(value = "模型分类标签，中文以逗号拼接", required = true)
    private String typesName;
}
