package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 规则排序信息
 * @author Marty
 */
@ApiModel(description= "规则排序信息")
@Data
public class SortedRule {
    @ApiModelProperty(value = "规则ID", required = true)
    private long ruleId;
    @ApiModelProperty(value = "排列序号", required = true)
    private int sortNo;
}
