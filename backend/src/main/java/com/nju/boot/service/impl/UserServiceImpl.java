package com.nju.boot.service.impl;

import com.nju.boot.entity.User;
import com.nju.boot.mapper.UserMapper;
import com.nju.boot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JingYa
 * @since 2023-07-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
