package com.nju.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slicer")
@Api(tags = "程序切片接口")
public class SlicerController {

    @Value("${files.upload.path}")
    private String fileUploadPath;


    @GetMapping("/dataflow")
    @ApiOperation(value = "基于数据流方程的切片")
    public String DataFlowEquationSlicing(String id, int lineNumber, String variable){
        return "";
    }

    @GetMapping("/pdg")
    @ApiOperation(value = "基于程序依赖图的切片")
    public String PDGSlicing(String id, int lineNumber, String variable){
        return "";
    }

    @GetMapping("/dynamic")
    @ApiOperation(value = "动态切片")
    public String DynamicSlicing(String id, int lineNumber, String variable){
        return "";
    }

}
