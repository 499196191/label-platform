package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 新增规则传输类
 * @author Marty
 */
@ApiModel(description= "规则信息传输类")
@Data
public class RuleInfoVo {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "规则名称")
    private String name;
    @ApiModelProperty(value = "规则类型 ENTITY(1, \"实体识别\"), TYPE(2, \"文本分类\"), RELATION(3, \"关系抽取\"), NOTSIGNED(4, \"其它\");")
    private Integer typeId;
    @ApiModelProperty(value = "识别结果")
    private String result;
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "规则标签ids")
    private List<Long> ruleTagIds;
    @ApiModelProperty(value = "配词信息（暂时是只考虑一行配词，此处数组为了后面拓展）")
    private List<RuleWordVo> ruleWords;
}
