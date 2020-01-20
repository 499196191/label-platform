package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签分类统计信息
 * @author Marty
 */
@ApiModel(description= "标签分类统计信息")
@Data
public class TagLabelCount {
    @ApiModelProperty(value = "未分类标签总数")
    private long noDesignedCount;
    @ApiModelProperty(value = "实体标签总数")
    private long entityTagCount;
    @ApiModelProperty(value = "分类标签总数")
    private long typeTagCount;
    @ApiModelProperty(value = "关系标签总数")
    private long relationTagCount;
}
