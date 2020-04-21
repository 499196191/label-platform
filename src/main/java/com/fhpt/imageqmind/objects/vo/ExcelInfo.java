package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 数据源信息
 * @author Marty
 */
@ApiModel(description= "文件解析信息")
@Data
public class ExcelInfo {
    @ApiModelProperty(value = "对象存储名称")
    private String objectName;
    @ApiModelProperty(value = "Excel的sheet分栏名称")
    private List<String> sheetNames;
    @ApiModelProperty(value = "Excel的字段名称信息")
    private List<ColumnInfo> columnInfos;
}
