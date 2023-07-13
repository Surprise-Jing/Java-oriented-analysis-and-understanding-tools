package com.nju.boot.controller;

import com.nju.boot.slicer.AbstractSlicer;
import com.nju.boot.slicer.DataFlowEquationSlicer;
import com.nju.boot.slicer.PDGSlicer;
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
    public String DataFlowEquationSlicing(String id, int lineNumber, String variable) throws Exception{
        if(lineNumber == 0 || variable.equals("")) {
            throw new Exception("切片参数不齐全");
        }
        String path = fileUploadPath + "/" + id + ".java";
        AbstractSlicer abstractSlicer = new DataFlowEquationSlicer(path);
        return abstractSlicer.slice(lineNumber,variable).getResultCode();
    }

    @GetMapping("/pdg")
    @ApiOperation(value = "基于程序依赖图的切片")
    public String PDGSlicing(String id, int lineNumber, String variable){
        String path = fileUploadPath + "/" + id + ".java";
        AbstractSlicer abstractSlicer = new PDGSlicer(path);
        return abstractSlicer.slice(lineNumber,variable).getResultCode();
    }

    @GetMapping("/dynamic")
    @ApiOperation(value = "动态切片")
    public String DynamicSlicing(String id, int lineNumber, String variable){
        return "";
    }

}
