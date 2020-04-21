package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据源信息
 * @author Marty
 */
@ApiModel(description= "数据库信息")
@Data
public class DbInfoVo {
    @ApiModelProperty(name="id",value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "连接名称")
    private String connectName;
    @ApiModelProperty(value = "数据库IP")
    private String ip;
    @ApiModelProperty(value = "数据库端口")
    private Integer port;
    @ApiModelProperty(value = "数据库用户名")
    private String userName;
    @ApiModelProperty(value = "数据库密码")
    private String password;
    @ApiModelProperty(value = "数据库名称")
    private String dbName;
    @ApiModelProperty(value = "数据库表名")
    private String tableName;
    @ApiModelProperty(value = "数据库schema")
    private String schema;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
}
