package com.fhpt.imageqmind.controller;

import com.fhpt.imageqmind.domain.UserEntity;
import com.fhpt.imageqmind.objects.Result;
import com.fhpt.imageqmind.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录相关接口
 */
@Api(value = "用户登录接口", description = "用户登录")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @ApiOperationSort(1)
    @PostMapping("/login")
    public Result<UserEntity> login(@RequestParam(value = "userName", required = true) String userName,
                                    @RequestParam(value = "password", required = true) String password){
        return userService.login(userName,password);
    }
    @ApiOperation(value = "用户新增")
    @ApiOperationSort(2)
    @PostMapping("/insert")
    public int insert(@RequestParam(value = "userName", required = true) String userName,
                         @RequestParam(value = "password", required = true) String password){
        return userService.insert(userName,password);
    }

    @ApiOperation(value = "用户密码修改")
    @ApiOperationSort(3)
    @PostMapping("/update")
    public boolean update(@RequestParam(value = "userName", required = true) String userName,
                         @RequestParam(value = "password", required = true) String password){
        return userService.update(userName,password);
    }
}
