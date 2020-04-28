package com.fhpt.imageqmind.objects.vo;

import com.fhpt.imageqmind.constant.method.Add;
import com.fhpt.imageqmind.constant.method.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * 标签信息类
 * @author Marty
 */
@ApiModel(description= "标签信息")
@Data
public class TagLabelVo {
    @Min(groups = Update.class, message = "更新时标签ID必须是个大于或等于1的数值", value = 1)
    @ApiModelProperty(name = "id", value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "标签名称")
    private String name;
    @ApiModelProperty(value = "标签类型（0未分类  1实体标签  2分类标签 3关系标签）")
    private Integer type;
    @ApiModelProperty(value = "标签英文名称")
    private String englishName;
    @ApiModelProperty(value = "标签样式类（弃用）")
    private String tagClass;
    @ApiModelProperty(value = "标签字体颜色（弃用）")
    private String fontColor;
    @ApiModelProperty(value = "标签背景颜色（弃用）")
    private String backgroudColor;
    @ApiModelProperty(value = "已标注量")
    private Long tagedsize;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "标签下的标注统计信息")
    private List<LabelResultCount> labelResultCount;
}
