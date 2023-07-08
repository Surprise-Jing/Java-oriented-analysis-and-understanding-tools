package com.nju.boot.controller;

import com.nju.boot.entity.User;
import com.nju.boot.entity.dto.LoginDto;
import com.nju.boot.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JingYa
 * @since 2023-07-06
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
public class UserController {

    @Resource
    private IUserService iUserService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Map<String, Object> login(@RequestBody LoginDto loginDto) throws Exception{
        User user = iUserService.login(loginDto);
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public User create(@RequestBody User user) throws Exception{
        return iUserService.create(user);
    }

    @PutMapping("")
    @ApiOperation(value = "更新用户")
    public User updateUser(@RequestBody User user){
        return iUserService.update(user);
    }


}
