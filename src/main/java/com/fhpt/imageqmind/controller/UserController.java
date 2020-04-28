package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.domain.UserEntity;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录相关接口
 */
@Api(value = "用户体系", description = "用户体系接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiOperationSort(1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "登录账号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query")
    })
    @PostMapping("/login")
    public Result<UserEntity> login(@RequestParam(value = "userCode", required = true) String userCode,
                                    @RequestParam(value = "password", required = true) String password){
        return userService.login(userCode,password);
    }
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiOperationSort(2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "登录账号", dataType = "String", paramType = "insert"),
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String", paramType = "insert"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "insert")
    })
    @PostMapping("/insert")
    public int insert(@RequestParam(value = "userCode", required = true) String userCode,
                      @RequestParam(value = "userName", required = true) String userName,
                         @RequestParam(value = "password", required = true) String password){
        return userService.insert(userCode,userName,password);
    }

    @ApiOperation(value = "用户密码修改")
    @ApiOperationSort(3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "登录账号", dataType = "String", paramType = "update"),
            @ApiImplicitParam(name = "userName", value = "用户名称(可选)", dataType = "String", paramType = "update"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "update")
    })
    @PostMapping("/update")
    public boolean update(@RequestParam(value = "userCode", required = true) String userCode,
                          @RequestParam(value = "userName", required = false) String userName,
                         @RequestParam(value = "password", required = true) String password){
        return userService.update(userCode,userName,password);
    }
}
