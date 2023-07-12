package com.nju.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.boot.entity.User;
import com.nju.boot.entity.Userfile;
import com.nju.boot.mapper.UserfileMapper;
import com.nju.boot.service.IUserfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JingYa
 * @since 2023-07-12
 */
@Service
public class UserfileServiceImpl extends ServiceImpl<UserfileMapper, Userfile> implements IUserfileService {

    @Resource
    private UserfileMapper userfileMapper;

    @Override
    public boolean delete(String fid, String uid) {
        QueryWrapper<Userfile> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("fid", fid);
        List<Userfile> userfileList = userfileMapper.selectList(queryWrapper);
        for(Userfile userfile: userfileList){
            if(userfile.getUserId() == uid){
                userfileMapper.deleteById(userfile);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Userfile> selectUserFileByUid(String uid) {
        QueryWrapper<Userfile> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("uid", uid);
        return userfileMapper.selectList(queryWrapper);
    }
}
