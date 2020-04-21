package com.fhpt.imageqmind.objects.vo;

import com.fhpt.imageqmind.objects.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据集详细信息
 * @author Marty
 */
@ApiModel(description= "数据集详细信息")
@Data
public class DataSetDetail {
    @ApiModelProperty(name="id",value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "数据集名称")
    private String name;
    @ApiModelProperty(value = "是否标注过")
    private boolean isTagged;
    @ApiModelProperty(value = "列的名称")
    private String columnName;
    @ApiModelProperty(value = "列的名称")
    private PageInfo<DataRowDetail> dataRowDetails;

    @ApiModel(description= "数据行详细信息")
    @Data
    public static class DataRowDetail{
        @ApiModelProperty(name="id",value = "主键id，新增时可以不传递", required = false)
        private long id;
        @ApiModelProperty(value = "内容")
        private String content;
        @ApiModelProperty(value = "实体标签（,拼接）")
        private String entityTags;
        @ApiModelProperty(value = "分类标签（,拼接）")
        private String classifyTags;
    }

}
