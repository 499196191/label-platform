package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;


import lombok.Data;

/**
 * 文件信息
 * @author Marty
 */
@ApiModel(description= "文件信息")
@Data
public class FileInfoVo {
    private long id;
    private String sheetName;
    private String path;
}
