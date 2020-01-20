package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



import java.util.List;

/**
 * 任务信息类
 * @author Marty
 */
@ApiModel(description= "任务信息")
@Data
public class TaskInfoVo {
    @ApiModelProperty(name="id", value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "任务名称")
    private String name;
    @ApiModelProperty(value = "任务类型信息")
    private TaskTypeVo taskType;
    @ApiModelProperty(value = "任务量")
    private Integer size;
    @ApiModelProperty(value = "任务状态")
    private Byte status;
    @ApiModelProperty(value = "任务进度")
    private double process;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "标签集合信息")
    private List<TagLabelVo> tagLabels;
}
