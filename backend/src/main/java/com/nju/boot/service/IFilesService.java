package com.nju.boot.service;

import com.nju.boot.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JingYa
 * @since 2023-07-07
 */
public interface IFilesService extends IService<Files> {

    public Files getFileByMd5(String md5);

    List<Files> getFilesByUid(String uid);

}
