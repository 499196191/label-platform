package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标注数据游标相关信息
 * @author Marty
 */
@Data
public class LabelIndexVo {
    @ApiModelProperty(value = "游标开始位置")
    private Integer start;
    @ApiModelProperty(value = "游标结束位置")
    private Integer end;
    @ApiModelProperty(value = "数据行ID")
    private Long dataRowId;
    @ApiModelProperty(value = "任务ID")
    private Long taskId;
    @ApiModelProperty(value = "标签ID")
    private Long tagId;
}
