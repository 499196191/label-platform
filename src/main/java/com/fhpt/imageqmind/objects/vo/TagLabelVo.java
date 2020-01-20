package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签信息类
 * @author Marty
 */
@ApiModel(description= "标签信息")
@Data
public class TagLabelVo {
    @ApiModelProperty(name="id", value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "标签名称")
    private String name;
    @ApiModelProperty(value = "标签类型（0未分类  1实体标签  2分类标签 3关系标签）")
    private Integer type;
    @ApiModelProperty(value = "标签英文名称")
    private String englishName;
    @ApiModelProperty(value = "标签样式类")
    private String tagClass;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
