package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 样本数据VO
 * @author xie
 */
@ApiModel(description= "样本数据VO")
@Data
public class SampleDataVo {
    @ApiModelProperty(value = "标签名")
    private String tagName;
    @ApiModelProperty(value = "样本总量")
    private Integer totalNum;
    @ApiModelProperty(value = "训练集")
    private Integer trainNum;
    @ApiModelProperty(value = "测试集")
    private Integer devNum;
    @ApiModelProperty(value = "验证集")
    private Integer testNum;
}
