package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 新增训练任务传输类
 * @author Marty
 */
@ApiModel(description= "新增(修改)训练任务传输类")
@Data
public class AddTrainingVo {
    @ApiModelProperty(value = "训练任务ID,修改时需传递", required = true)
    private Long id;
    @ApiModelProperty(value = "训练任务名称", required = true)
    private String name;
    @ApiModelProperty(value = "训练类型：1实体识别2文本分类3关系抽取", required = true)
    private Integer type;
    @ApiModelProperty(value = "训练模型类型", required = true)
    private Integer modelType;
    @ApiModelProperty(value = "训练集比例", required = true)
    private double trainingPercent;
    @ApiModelProperty(value = "验证集比例", required = true)
    private double validatePercent;
    @ApiModelProperty(value = "测试集比例", required = true)
    private double testPercent;
    @ApiModelProperty(value = "迭代次数", required = true)
    private Integer iterationTimes;
    @ApiModelProperty(value = "批量大小", required = true)
    private Integer batchSize;
    @ApiModelProperty(value = "学习率", required = true)
    private double learningRate;
    @ApiModelProperty(value = "标记任务结果集id数组", required = true)
    private List<Long> taskIds;
    @ApiModelProperty(value = "训练标签id数组", required = true)
    private List<Long> tagIds;
}
