package com.fhpt.imageqmind.objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果
 * @author Marty
 */
@Data
@NoArgsConstructor
@ApiModel(description= "分页查询结果信息")
public class PageInfo<T> {
    @ApiModelProperty(value = "总数")
    private long total;
    @ApiModelProperty(value = "当前页列表内容")
    private List<T> list;
}
