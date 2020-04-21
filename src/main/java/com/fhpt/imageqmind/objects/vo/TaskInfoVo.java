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
    @ApiModelProperty(value = "任务类型名称")
    private String taskTypeName;
    @ApiModelProperty(value = "任务量")
    private Integer size;
    @ApiModelProperty(value = "已标注数据量")
    private Integer finishedSize;
    @ApiModelProperty(value = "任务状态")
    private Integer status;
    @ApiModelProperty(value = "任务状态名称")
    private String statusName;
    @ApiModelProperty(value = "任务进度")
    private double process;
    @ApiModelProperty(value = "当前行ID（如果为-1表示当前无法标注，原因可能是：1.有人正在标注，占用资源 2.标注任务已完成）")
    private Long currentRowId;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "标签集合信息")
    private List<TagLabelVo> tagLabels;
    @ApiModelProperty(value = "标签名称，逗号拼接。如tag1,tag2")
    private String tagNames;
    @ApiModelProperty(value = "数据集名称")
    private String dataSetName;
    @ApiModelProperty(value = "数据集总量")
    private Integer dataSetSize;
    @ApiModelProperty(value = "任务进度信息")
    private List<LabelProcessInfo> labelProcessInfo;
    @ApiModelProperty(value = "今日标注数据量")
    private long taskTodaySize;
    @ApiModelProperty(value = "总标注数据量")
    private long taskAllSize;
}
