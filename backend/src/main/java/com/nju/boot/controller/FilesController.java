package com.nju.boot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.kitfox.svg.Use;
import com.nju.boot.entity.Files;
import com.nju.boot.entity.Userfile;
import com.nju.boot.entity.dto.FileDto;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.handler.DisableBaseResponse;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.mapper.UserfileMapper;
import com.nju.boot.service.IFilesService;
import com.nju.boot.service.IUserfileService;
import com.nju.boot.service.impl.FilesServiceImpl;
import com.nju.boot.utils.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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

    @Resource
    private FilesMapper filesMapper;

    @Resource
    private IUserfileService iUserfileService;

    @PostMapping("")
    @ApiOperation(value = "上传文件")
    public Files uploadFile(String uid, MultipartFile file) throws Exception {
        System.out.println(uid);
        if(file == null) throw new Exception("请求参数缺失");
        if(file.isEmpty()){
            throw new Exception("上传失败，请选择文件");
        }
        File uploadParentFile = new File(fileUploadPath);
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }

        String md5 = SecureUtil.md5(file.getInputStream());
        String url;
        Files files = iFilesService.getFileByMd5(md5);
        if(files != null){
            url = files.getUrl();
            if(files.getEnable()){
               files.setDeleted(false);
               files.setUploadTime(DateTimeUtils.getNowTimeString());
               filesMapper.updateById(files);
            }
        }
        else {
            String uuId = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            String type = FileUtil.extName(originalFilename);
            String fileUUID = uuId + StrUtil.DOT + type;
            File uploadFile = new File(fileUploadPath + "/" + fileUUID);
            file.transferTo(uploadFile);
            url = "http://" + serverAddress + ":" + serverPort + "/file?id=" + fileUUID;
            files = new Files(uuId, originalFilename, type, md5, url, DateTimeUtils.getNowTimeString(), false, true);
            iFilesService.save(files);
        }
        Userfile userfile = new Userfile(UUID.randomUUID().toString(), uid, files.getId());
        iUserfileService.save(userfile);
        return files;
    }

    @GetMapping("")
    @ApiOperation(value = "获取文件内容")
    public String getFileContent(@RequestParam("id") String id) throws Exception{ //流请求还是字符串请求？
        if("".equals(id)){
            return "";
        }
        String path = fileUploadPath + "/" + id + ".java";
        File file = new File(path);
        return FileUtils.readFileToString(file, "utf-8");
    }

    @GetMapping("/user")
    @ApiOperation(value = "获得用户上传的所有文件")
    public List<FileDto> getUserFiles(@RequestParam("uid") String uid){
        List<FileDto> fileDtoList = new LinkedList<>();
        List<Userfile> userfileList = iUserfileService.selectUserFileByUid(uid);
        for(Userfile userfile: userfileList){
            Files files = iFilesService.getById(userfile.getFid());
            fileDtoList.add(new FileDto(files.getId(), files.getName(), files.getUploadTime()));
        }
        return fileDtoList;
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除文件")
    public boolean deleteFile(String id, String uid){
        return iUserfileService.delete(id, uid);
    }

}

