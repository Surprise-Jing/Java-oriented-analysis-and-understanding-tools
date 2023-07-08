package com.nju.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nju.boot.entity.Files;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.service.IFilesService;
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
 * @since 2023-07-07
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    @Resource
    private FilesMapper filesMapper;

    @Override
    public Files getFileByMd5(String md5) {
        LambdaQueryWrapper<Files> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Files::getMd5, md5);
        List<Files> list = this.list(queryWrapper);
        return list.size() == 0 ? null : list.get(0);
    }
}
