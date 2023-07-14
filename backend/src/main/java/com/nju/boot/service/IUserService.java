package com.nju.boot.service;

import com.nju.boot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nju.boot.entity.dto.LoginDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JingYa
 * @since 2023-06-28
 */
public interface IUserService extends IService<User> {

    User login(LoginDto loginDto) throws Exception;

    User create(User user) throws Exception;

    User update(User user);

    User findByUsername(String username);

}
