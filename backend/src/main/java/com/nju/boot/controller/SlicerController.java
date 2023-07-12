package com.nju.boot.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slicer")
@Api(tags = "程序切片接口")
public class SlicerController {

    @Value("${files.upload.path}")
    private String fileUploadPath;


}
