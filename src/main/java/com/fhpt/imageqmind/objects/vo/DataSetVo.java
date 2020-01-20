package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据集传输体
 */
@ApiModel(description= "数据集数据")
@Data
public class DataSetVo {
    @ApiModelProperty(name="id",value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "数据集名称")
    private String name;
    @ApiModelProperty(value = "数据集类型，1为oracle数据源，2为excel数据，3为csv数据")
    private Integer type;
    @ApiModelProperty(value = "数据集大小")
    private Integer size;
    @ApiModelProperty(value = "数据集描述信息")
    private String describe;
    @ApiModelProperty(value = "更新时间", allowableValues = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
    @ApiModelProperty(value = "创建时间", allowableValues = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    @ApiModelProperty(value = "数据库信息")
    private DbInfoVo dbInfo;
    @ApiModelProperty(value = "列名称")
    private String columnName;
}
