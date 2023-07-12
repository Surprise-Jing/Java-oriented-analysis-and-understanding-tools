package com.nju.boot.controller;

import com.nju.boot.entity.Files;
import com.nju.boot.entity.User;
import com.nju.boot.entity.dto.LoginDto;
import com.nju.boot.service.IFilesService;
import com.nju.boot.service.IUserService;
import com.nju.boot.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.LifecycleState;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Resource
    private IFilesService iFilesService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Map<String, Object> login(@RequestBody LoginDto loginDto) throws Exception{
        User user = iUserService.login(loginDto);
        Map<String, Object> map = new HashMap<>();
        //过期时间
        long exp = JwtTokenUtils.EXPIRATION_TIME;
        map.put("token", JwtTokenUtils.createToken(loginDto.getUsername(), exp));
        map.put("user", user);
        return map;
    }

    @GetMapping("/info")
    @ApiOperation(value = "获得用户信息")
    public User getInfo(String token){
        String username = JwtTokenUtils.getUsername(token);
        User user = iUserService.findByUsername(username);
        return user;
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户登出")
    public boolean Logout(){
        return true;
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

    @GetMapping("/file")
    @ApiOperation(value = "获得用户上传的所有文件")
    public List<Files> getUserFiles(@RequestParam("uid") String uid){
        return iFilesService.getFilesByUid(uid);
    }

}
