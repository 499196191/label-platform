package com.fhpt.imageqmind.objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回结构体
 * @author Marty
 */
@ApiModel(description= "返回响应数据")
public class Result<T> {

    @ApiModelProperty(value = "是否成功，0代表成功，-1代表失败")
    public int code;
    @ApiModelProperty(value = "返回服务器信息")
    public String msg;
    @ApiModelProperty(value = "返回实体数据")
    public T data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result() {}
}
