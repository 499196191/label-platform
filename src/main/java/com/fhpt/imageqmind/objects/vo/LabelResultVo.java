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
    private int id;
    @ApiModelProperty(value = "游标开始位置")
    private Integer start;
    @ApiModelProperty(value = "游标结束位置")
    private Integer end;
    //数据行
    @ApiModelProperty(value = "数据行信息")
    private DataRowVo dataRow;
    //任务信息
    @ApiModelProperty(value = "任务信息")
    private TaskInfoVo taskInfo;
    //标签信息
    @ApiModelProperty(value = "标签信息")
    private TagLabelVo tagLabel;
}
