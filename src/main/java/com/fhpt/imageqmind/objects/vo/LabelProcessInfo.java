package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * 标注进度相关信息
 * @author Marty
 */
@Data
public class LabelProcessInfo {
    @ApiModelProperty(value = "标注人")
    private String createdBy;
    @ApiModelProperty(value = "今日标注数据量")
    private long todaySize;
    @ApiModelProperty(value = "总标注数据量")
    private long allSize;
    @ApiModelProperty(value = "开始标注时间")
    private String startTime;
    @ApiModelProperty(value = "最后标注时间")
    private String endTime;
}
