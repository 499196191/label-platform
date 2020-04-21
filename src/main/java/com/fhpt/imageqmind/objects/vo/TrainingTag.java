package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练任务详细信息
 * @author Marty
 */
@ApiModel(description= "训练标签详细信息")
@Data
public class TrainingTag {
    @ApiModelProperty(value = "标签名称")
    private String tagName;
    @ApiModelProperty(value = "准确率")
    private double precision;
    @ApiModelProperty(value = "召回率")
    private double recall;
    @ApiModelProperty(value = "F值")
    private double F;
}
