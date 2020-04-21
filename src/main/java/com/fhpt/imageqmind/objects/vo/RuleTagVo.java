package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 规则标签信息
 * @author Marty
 */
@ApiModel(description= "规则标签信息")
@Data
public class RuleTagVo {

    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "标签名称")
    private String name;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "数量")
    private Long size;
}
