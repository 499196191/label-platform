package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;


import lombok.Data;


import java.util.List;

/**
 * 数据源信息
 * @author Marty
 */
@ApiModel(description= "文件字段信息")
@Data
public class ColumnInfo {
    private String sheetName;
    private List<String> columnNames;
}
