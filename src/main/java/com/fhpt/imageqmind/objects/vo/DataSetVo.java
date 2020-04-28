package com.fhpt.imageqmind.objects.vo;

import com.fhpt.imageqmind.constant.method.Add;
import com.fhpt.imageqmind.constant.method.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * 数据集传输体
 */
@ApiModel(description= "数据集数据")
@Data
public class DataSetVo {
    @PositiveOrZero(groups = Update.class, message = "更新时数据集ID必须是个数值")
    @Null(groups = Add.class, message = "新增时数据集ID必须为空")
    @ApiModelProperty(name="id",value = "主键id，新增时可以不传递", required = false)
    private long id;
    @ApiModelProperty(value = "数据集名称")
    private String name;
    @ApiModelProperty(value = "数据集类型，1为oracle数据源，2为mysql数据源，3为excel数据，4为csv数据，5为pg数据源")
    private Integer type;
    @ApiModelProperty(value = "数据集大小")
    private Integer size;
//    @ApiModelProperty(value = "定义同步数据最大值")
//    private Integer maxSize;
    @ApiModelProperty(value = "数据集描述信息")
    private String describe = "";
    @ApiModelProperty(value = "更新时间", allowableValues = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
    @ApiModelProperty(value = "创建时间", allowableValues = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    @ApiModelProperty(value = "数据库信息")
    private DbInfoVo dbInfo;
    @ApiModelProperty(value = "文件信息")
    private FileInfoVo fileInfo;
    @ApiModelProperty(value = "列名称")
    private String columnName;
    @ApiModelProperty(value = "分类名称集（逗号拼接）")
    private String typeNames;
    @ApiModelProperty(value = "关联标注任务数量")
    private Long relateTaskNum;
    @ApiModelProperty(value = "关联标注任务列表信息")
    private List<TaskInfoVo> taskInfoVos;

}
