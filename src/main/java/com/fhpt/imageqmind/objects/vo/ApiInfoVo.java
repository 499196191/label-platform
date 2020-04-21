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
public class ApiInfoVo {
    @ApiModelProperty(name="id", value = "主键id", required = false)
    private long id;
    @ApiModelProperty(value = "请求地址")
    private String apiUrl;
    @ApiModelProperty(value = "请求方式")
    private String method;
    @ApiModelProperty(value = "请求参数")
    private String paramJson;
    @ApiModelProperty(value = "返回结果")
    private String returnFormat;
}
