package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务类型信息
 * @author Marty
 */
@ApiModel(description= "任务类型信息")
@Data
public class TaskTypeVo {
    @ApiModelProperty(name="id", value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "类型名称")
    private String name;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
