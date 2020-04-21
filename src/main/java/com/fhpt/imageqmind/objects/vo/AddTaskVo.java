package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 新增任务传输类
 * @author Marty
 */
@ApiModel(description= "新增任务传输类")
@Data
public class AddTaskVo {
    @ApiModelProperty(value = "任务名称")
    private String name;
    @ApiModelProperty(value = "任务类型信息")
    private Long taskType;
    @ApiModelProperty(value = "标签Ids")
    private List<Long> tagIds;
    @ApiModelProperty(value = "数据集Id")
    private Long dataSetId;
    @ApiModelProperty(value = "任务目标量")
    private int size;
}
