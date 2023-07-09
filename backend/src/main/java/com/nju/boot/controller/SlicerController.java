package com.nju.boot.controller;

import com.nju.boot.slicer.Graphs;
import com.nju.boot.util.SlicerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slicer")
@Api(tags = "程序切片接口")
public class SlicerController {

    @Value("${files.upload.path}")
    private String fileUploadPath;


}
