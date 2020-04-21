package com.fhpt.imageqmind.objects.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 标注数据游标已经其它详细信息
 * @author Marty
 */
@ApiModel(description= "标注数据游标已经其它详细信息")
@Data
public class LabelResultDetail {
    @ApiModelProperty(value = "游标信息")
    private List<LabelResultVo> list;
    @ApiModelProperty(value = "标注任务相关信息")
    private TaskInfoVo taskInfo;
}
