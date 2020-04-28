package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.annotation.EagleEye;
import com.fhpt.imageqmind.constant.method.Add;
import com.fhpt.imageqmind.exceptions.DataSetNameVerifyException;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.objects.vo.DataSetVo;
import com.fhpt.imageqmind.objects.vo.ExcelInfo;
import com.fhpt.imageqmind.service.DataSetService;
import com.fhpt.imageqmind.utils.ExcelUtil;
import com.fhpt.imageqmind.utils.MinioUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据集相关接口
 * @author Marty
 */
@Slf4j
@Api(value = "数据集相关接口", description = "基于数据集的操作")
@ApiSort(1)
@Validated
@RestController
@RequestMapping("/dataSet")
public class DataSetController {

    @Autowired
    private DataSetService dataSetService;

    @EagleEye
    @ApiOperation(value = "查询所有")
    @ApiOperationSort(4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码(从1开始)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sourceType", value = "数据源类型", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "typeNames", value = "分类名称（逗号拼接）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "数据集名称", dataType = "String", paramType = "query")
    })
    @GetMapping("/list")
    public Result<PageInfo<DataSetVo>> list(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                            @RequestParam(value = "sourceType", defaultValue = "-1", required = false) Integer sourceType,
                                            @RequestParam(value = "typeNames", defaultValue = "", required = false) String typeNames,
                                            @RequestParam(value = "name", defaultValue = "", required = false) String name) {
        Result<PageInfo<DataSetVo>> result = new Result<>();
        result.code = 0;
        result.data = dataSetService.query(page, pageSize, sourceType, typeNames, name);
        result.msg = "查询成功";
        return result;
    }

    @ApiOperation(value = "新增数据集", notes = "用户可以通过此接口新增单个的数据集，注意：数据集的数据源可能有oracle、excel、csv几种格式")
    @ApiOperationSort(1)
    @PostMapping("/add")
    public Result<DataSetVo> add(@RequestBody @Validated(Add.class) DataSetVo dataSet) {
        Result<DataSetVo> result = new Result<>();
        result.data = dataSetService.insert(dataSet);
        if (result.data.getId() != 0) {
            result.code = 0;
            result.msg = "新增成功";
        } else {
            result.code = -1;
            result.msg = "新增失败";
        }
        return result;
    }

    @ApiOperation(value = "验证数据集名称是否重复", notes = "表单验证时调用此接口")
    @GetMapping("/verifyName")
    public Result<Boolean> verifyName(@RequestParam("name") @NotBlank(message = "名称不能为空") String name){
        Result<Boolean> result = new Result<>();
        try {
            if (dataSetService.verifyName(name)) {
                result.code = 0;
                result.data = true;
                result.msg = "该名称可以使用";
            }
        } catch (DataSetNameVerifyException e) {
            result.code = 0;
            result.data = false;
            result.msg = e.getMessage();
        }
        return result;
    }

    @ApiOperation(value = "更新数据集", notes = "用户可以通过此接口修改单个的数据集，注意：数据集的数据源可能有oracle、excel、csv几种格式")
    @ApiOperationSort(2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "数据集名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "typeNames", value = "数据集分类标签（分类标签名称以逗号分隔）", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "describe", value = "描述信息", dataType = "String", paramType = "query")
    })
    @PostMapping("/update")
    public Result<Boolean> update(Long id, String name, String typeNames, String describe) {
        Result<Boolean> result = new Result<>();
        result.data = dataSetService.update(id, name, typeNames, describe);
        if (result.data) {
            result.code = 0;
            result.msg = "更新成功";
        } else {
            result.code = -1;
            result.msg = "更新失败";
        }
        return result;
    }

    @ApiOperation(value = "删除数据集")
    @ApiOperationSort(3)
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query")})
    @GetMapping("/delete")
    public Result delete(Long id) {
        Result result = new Result<>();
        try {
            if (dataSetService.delete(id)) {
                result.code = 0;
                result.msg = "删除成功";
            } else {
                result.code = -1;
                result.msg = String.format("删除失败:未找到数据集[id]为[%d]的数据", id);
            }
        } catch (Exception e) {
            result.code = -1;
            result.msg = "删除失败:数据库异常";
        }
        return result;
    }

    @ApiOperation(value = "连接数据集，检查是否连接成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "数据源类型", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "ip", value = "ip", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "port", value = "端口", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dbName", value = "数据库名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tableName", value = "表名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "columnName", value = "列名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "schema", value = "schema", dataType = "String", paramType = "query")

    })
    @PostMapping("/connect")
    public Result connectDb(int sourceType, String ip, int port, String userName, String password, String dbName, String tableName, String columnName, String schema) {
        //连接检查
        Result<Boolean> result = new Result<>();
        result.code = 0;
        result.msg = "测试完成";
        result.data = dataSetService.connectDB(sourceType, ip, port, userName, password, dbName, tableName, columnName, schema);
        return result;
    }

    @ApiOperation(value = "数据集excel文件上传")
    @ApiOperationSort(5)
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "file", dataType = "file", paramType = "form")})
    @PostMapping("/resolve")
    public Result<ExcelInfo> resolveFile(MultipartFile file){
        Result<ExcelInfo> result = new Result<>();
        ExcelInfo fileInfo = new ExcelInfo();
        //保存进对象存储服务器
        String objectName = MinioUtil.putExcel(file);
        try {
            fileInfo = ExcelUtil.readAllSheetsName(file.getInputStream(), file.getOriginalFilename());
            fileInfo.setObjectName(objectName);
            result.code = 0;
            result.msg = "文件解析成功";
        } catch (IOException e) {
            e.printStackTrace();
            result.code = -1;
            result.msg = "文件解析失败";
        }
        result.data = fileInfo;
        return result;
    }

    @ApiOperation(value = "数据集excel文件删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "objectName", value = "objectName", dataType = "string", paramType = "query")})
    @PostMapping("/removeFile")
    public Result deleteFile(String objectName){
        Result result = new Result<>();
        if (MinioUtil.deleteObject(objectName)) {
            result.code = 0;
            result.msg = "文件删除成功";
        } else {
            result.code = -1;
            result.msg = "文件删除失败";
        }
        return result;
    }

    @ApiOperation(value = "数据集分类标签名")
    @GetMapping("/typeNames")
    public Result<Set<String>> getAllTypeNames() {
        Result<Set<String>> result = new Result<>();
        result.code = 0;
        result.msg = "查询成功";
        result.data = dataSetService.getAllTypeNames();
        return result;
    }

    @ApiOperation(value = "根据数据集ID查询数据集分类标签名")
    @ApiImplicitParams({@ApiImplicitParam(name = "dataSetIds", value = "数据集ids，逗号拼接", dataType = "String", paramType = "query")})
    @GetMapping("/typeNamesByIds")
    public Result<Set<String>> getTrainingTypeNames(String dataSetIds) {
        Result<Set<String>> result = new Result<>();
        List<Long> dataSetArray = new ArrayList();
        if (StringUtils.hasText(dataSetIds)) {
            dataSetArray = Arrays.stream(dataSetIds.split(",")).map(t -> Long.parseLong(t)).collect(Collectors.toList());
        }
        result.code = 0;
        result.msg = "查询成功";
        result.data = dataSetService.getTypeNamesByIds(dataSetArray);
        return result;
    }

    @ApiOperation(value = "下载示例Excel文件接口")
    @GetMapping("/sampleExcel/download")
    public void downloadExcel(HttpServletResponse response){
        // 导出的EXCEL文件名
        String exportFileName = "示例.xlsx";
        response.setContentType("octets/stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(exportFileName.getBytes("UTF-8"), "iso8859-1") + "\"");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            InputStream inputStream = DataSetController.class.getClassLoader().getResourceAsStream("files/sample.xlsx");
            int len = -1;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                servletOutputStream.write(bytes, 0, len);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("示例文件导出失败！");
        }
    }


}
