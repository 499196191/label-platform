package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练任务信息
 * @author Marty
 */
@ApiModel(description= "训练任务信息")
@Data
public class TrainingInfoVo {
    @ApiModelProperty(value = "训练任务ID")
    private Long id;
    @ApiModelProperty(value = "训练任务名称")
    private String name;
    @ApiModelProperty(value = "训练任务类型")
    private Integer type;
    @ApiModelProperty(value = "模型类型CHARACTER_BASED(1, \"基于字符的BiLSTM+CRF模型\"), WORD_SEGMENTATION(2, \"基于分词的BiLSTM+CRF模型\");")
    private Integer modelType;
    @ApiModelProperty(value = "训练集百分比")
    private double trainingPercent;
    @ApiModelProperty(value = "验证集百分比")
    private double validatePercent;
    @ApiModelProperty(value = "测试集百分比")
    private double testPercent;
    @ApiModelProperty(value = "迭代次数")
    private Integer iterationTimes;
    @ApiModelProperty(value = "批量大小")
    private Integer batchSize;
    @ApiModelProperty(value = "学习率")
    private double learningRate;
    @ApiModelProperty(value = "训练状态")
    private Integer trainingStatus;
    @ApiModelProperty(value = "错误信息")
    private String failMsg;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "训练UUID")
    private String taskId;
    @ApiModelProperty(value = "已训练时长(分钟)")
    private String costTime;
}
