package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模型信息类
 * @author xie
 */
@ApiModel(description= "模型信息")
@Data
public class ModelInfoVo {
    @ApiModelProperty(name="id", value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "模型名称")
    private String modelName;
    @ApiModelProperty(value = "模型类型（ENTITY(1, \"实体识别\"), TYPE(2, \"文本分类\"), RELATION(3, \"关系抽取\")）",required = true)
    private Integer modelType;
    @ApiModelProperty(value = "版本")
    private String version;
    @ApiModelProperty(value = "发布状态")
    private Integer deployStatus;
    @ApiModelProperty(value = "模型来源")
    private Integer sourceType;
    @ApiModelProperty(value = "分类标签")
    private String typesName;
    @ApiModelProperty(value = "模型简介")
    private String brief;
    @ApiModelProperty(value = "训练ID")
    private long trainingId;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
