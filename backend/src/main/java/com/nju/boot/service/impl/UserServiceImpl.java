package com.nju.boot.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.boot.entity.User;
import com.nju.boot.entity.dto.LoginDto;
import com.nju.boot.mapper.UserMapper;
import com.nju.boot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nju.boot.utils.DateTimeUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JingYa
 * @since 2023-06-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User login(LoginDto loginDto) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("username", loginDto.getUsername());
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            throw new Exception("用户名或密码错误");
        }
        if(!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new Exception("用户名或密码错误");
        }
        user.setUpdateAt(DateTimeUtils.getNowTimeString());
        userMapper.updateById(user);
        return user;
    }

    @Override
    public User create(User user) throws Exception {
        if(findByUsername(user.getUsername()) != null){
            throw new Exception("用户名已注册");
        }
        user.setId(UUID.randomUUID().toString());
        user.setUpdateAt(DateTimeUtils.getNowTimeString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        return user;
    }

    @Override
    public User update(User user) {
        User oldUser = userMapper.selectById(user.getId());
        user.setUpdateAt(DateTimeUtils.getNowTimeString());
        userMapper.updateById(user);
        if(!oldUser.getPassword().equals(bCryptPasswordEncoder.encode(user.getPassword()))) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("username", username);
        return userMapper.selectOne(queryWrapper);
    }
}
