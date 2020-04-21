package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签标注统计信息
 * @author Marty
 */
@ApiModel(description= "标签标注统计信息")
@Data
public class LabelResultCount {
    @ApiModelProperty(value = "标注内容")
    private String content;
    @ApiModelProperty(value = "标注内容出现次数")
    private long count;
}
