package com.nju.boot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.nju.boot.entity.Files;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.service.IFilesService;
import com.nju.boot.service.impl.FilesServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JingYa
 * @since 2023-07-07
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传接口")
public class FilesController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.address}")
    private String serverAddress;

    @Resource
    private IFilesService iFilesService;

    @PostMapping("")
    @ApiOperation(value = "上传文件")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        //获取文件
        File uploadParentFile = new File(fileUploadPath);
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        //定义文件唯一标识码UUID
        String uuid = UUID.randomUUID().toString();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID);

        String url;
        String md5 = SecureUtil.md5(file.getInputStream());
        //判断文件在数据库中是否已经存在
        Files dbFiles = iFilesService.getFileByMd5(md5);
        if(dbFiles != null){
            url = dbFiles.getUrl();
        }
        else{
            //将临时文件转存到指定磁盘位置
            file.transferTo(uploadFile);
            url = "http://" + serverAddress + ":" + serverPort + "/file?id=" + fileUUID;
        }

        //存储至数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setMd5(md5);
        saveFile.setUrl(url);
        saveFile.setUploadTime(LocalDateTime.now());
        iFilesService.save(saveFile);
        return url;
    }

    @GetMapping("")
    public String get(){
        CFG cfg = new CFG();
        return "yes";
    }

}
