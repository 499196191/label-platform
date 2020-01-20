package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据行信息
 * @author Marty
 */
@ApiModel(description= "数据行信息")
@Data
public class DataRowVo {
    @ApiModelProperty(name="id", value = "主键id，新增时可以不传递", required = false)
    private int id;
    @ApiModelProperty(value = "数据集信息")
    private DataSetVo dataSet;
    @ApiModelProperty(value = "行号")
    private Integer row;
    @ApiModelProperty(value = "文本内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
}
