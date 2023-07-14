package com.nju.boot.service;

import com.nju.boot.entity.User;
import com.nju.boot.entity.Userfile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JingYa
 * @since 2023-07-12
 */
public interface IUserfileService extends IService<Userfile> {

    boolean delete(String fid, String uid);

    List<Userfile> selectUserFileByUid(String uid);

}
