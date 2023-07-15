package com.nju.boot.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nju.boot.entity.Files;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
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
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.address}")
    private String serverAddress;
    @Resource
    private FilesMapper filesMapper;

    @Override
    public Files getFileByMd5(String md5) {
        Files files = filesMapper.selectOne(new QueryWrapper<Files>().eq("md5", md5));
        return files;
    }

    @Override
    public List<Files> getFilesByUid(String uid) {
        return filesMapper.selectList(new QueryWrapper<Files>().in("uid", uid));
    }
}
