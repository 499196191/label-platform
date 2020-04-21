package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签标注信息
 * @author Marty
 */
@ApiModel(description= "标签标注信息")
@Data
public class LabelResultVo {
    @ApiModelProperty(name="id",value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "游标开始位置")
    private Integer start;
    @ApiModelProperty(value = "游标结束位置")
    private Integer end;
    @ApiModelProperty(value = "颜色数组索引")
    private Integer colorIndex;
    @ApiModelProperty(value = "划词内容")
    private String content;
    @ApiModelProperty(value = "数据行信息")
    private DataRowVo dataRow;
    @ApiModelProperty(value = "任务信息")
    private TaskInfoVo taskInfo;
    @ApiModelProperty(value = "标签信息")
    private TagLabelVo tagLabel;
}
