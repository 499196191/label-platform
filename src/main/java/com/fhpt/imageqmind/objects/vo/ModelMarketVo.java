package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模型信息类
 * @author xie
 */
@ApiModel(description= "模型信息")
@Data
public class ModelMarketVo {
    @ApiModelProperty(name="id", value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "模型名称")
    private String modelName;
    @ApiModelProperty(value = "模型ID",required = true)
    private long modelId;
    @ApiModelProperty(value = "请求文本")
    private String request;
    @ApiModelProperty(value = "响应文本")
    private String response;
    @ApiModelProperty(value = "部署状态")
    private int deployStatus;
    @ApiModelProperty(value = "调用次数")
    private int callTime;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
