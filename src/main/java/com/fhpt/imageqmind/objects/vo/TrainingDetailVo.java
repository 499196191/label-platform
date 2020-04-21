package com.fhpt.imageqmind.objects.vo;

import com.fhpt.imageqmind.domain.TaskInfoEntity;

import io.swagger.annotations.ApiModel;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



import java.util.List;

/**
 * 训练任务详细信息
 * @author Marty
 */
@ApiModel(description= "训练任务详细信息")
@Data
public class TrainingDetailVo {
    @ApiModelProperty(value = "训练任务ID")
    private Long id;
    @ApiModelProperty(value = "训练任务名称")
    private String name;
    @ApiModelProperty(value = "训练类型")
    private Integer type;
    @ApiModelProperty(value = "训练模型类型")
    private Integer modelType;
    @ApiModelProperty(value = "训练集比例")
    private double trainingPercent;
    @ApiModelProperty(value = "验证集比例")
    private double validatePercent;
    @ApiModelProperty(value = "测试集比例")
    private double testPercent;
    @ApiModelProperty(value = "迭代次数")
    private Integer iterationTimes;
    @ApiModelProperty(value = "批量大小")
    private Integer batchSize;
    @ApiModelProperty(value = "学习率")
    private double learningRate;
    @ApiModelProperty(value = "训练状态：NOT_START(0, \"未开始\"), TRAINING(1, \"进行中\"), FINISHED(2, \"已完成\"), FAILED(3, \"失败\"), STOPPED(4, \"已停止\")")
    private Integer trainingStatus;
    @ApiModelProperty(value = "模型效果")
    private String trainingResult;
    @ApiModelProperty(value = "错误信息")
    private String failMsg;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "完成时间")
    private String finishTime;
    @ApiModelProperty(value = "已训练时长(分钟)")
    private Long costTime;
    @ApiModelProperty(value = "训练任务UUID")
    private String taskId;
    @ApiModelProperty(value = "标注结果集")
    private List<TaskInfoVo> taskInfoVos;
    @ApiModelProperty(value = "总数据量")
    private int allTaskSize;
    @ApiModelProperty(value = "损失函数值loss")
    private double loss;
    @ApiModelProperty(value = "总样本量")
    private long allSampleSize;
    @ApiModelProperty(value = "训练标签结果")
    private List<TrainingTag> trainingTags;
    @ApiModelProperty(value = "样本数据统计结果")
    private List<SampleDataVo> sampleDataVos;
    @ApiModelProperty(value = "结果标签名,隔开")
    private String trainingTagNames;

}
