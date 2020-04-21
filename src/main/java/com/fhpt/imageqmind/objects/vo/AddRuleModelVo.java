package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



import java.util.List;

/**
 * 新增规则模型传输类
 * @author Marty
 */
@ApiModel(description= "新增规则模型传输类")
@Data
public class AddRuleModelVo extends AddModelVo {
    @ApiModelProperty(value = "输出结果数量",required = true)
    private int resultType;
    @ApiModelProperty(value = "规则排序信息列表",required = true)
    private List<SortedRule> sortedRules;
    @ApiModelProperty(value = "模型版本",required = true)
    private String version;
    @ApiModelProperty(value = "模型介绍",required = false)
    private String brief;
}
