package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 规则词
 * @author Marty
 */
@ApiModel(description= "规则词")
@Data
public class RuleWordVo {
    @ApiModelProperty(value = "id(新增时不需要传递)")
    private Long id;
    @ApiModelProperty(value = "左边配词")
    private String lWord;
    @ApiModelProperty(value = "右边配词")
    private String rWord;
    @ApiModelProperty(value = "词距")
    private Integer wordSpacing;
    @ApiModelProperty(value = "排列序号（0开始计算）")
    private Integer sortNo;
}
