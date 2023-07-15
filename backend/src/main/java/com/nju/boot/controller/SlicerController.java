package com.nju.boot.controller;

import cn.hutool.setting.Setting;
import com.ibm.jdi.DynamicSlicer;
import com.nju.boot.entity.dto.StringDto;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.slicer.AbstractSlicer;
import com.nju.boot.slicer.DataFlowEquationSlicer;
import com.nju.boot.slicer.PDGSlicer;
import com.nju.boot.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/slicer")
@Api(tags = "程序切片接口")
public class SlicerController {

    @Resource
    private FilesMapper filesMapper;


    @GetMapping("/dataflow")
    @ApiOperation(value = "基于数据流方程的切片")
    public StringDto DataFlowEquationSlicing(String id, int lineNumber, String variable) throws Exception{
        if(lineNumber == 0 || variable.equals("")) {
            throw new Exception("切片参数不齐全");
        }
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILE_SRC_PATH + "/" + fileName;
        AbstractSlicer abstractSlicer = new DataFlowEquationSlicer(path);
        return new StringDto(abstractSlicer.slice(lineNumber,variable).getResultCode());
    }

    @GetMapping("/pdg")
    @ApiOperation(value = "基于程序依赖图的切片")
    public StringDto PDGSlicing(String id, int lineNumber, String variable) throws Exception {
        if(lineNumber == 0 || variable.equals("")) {
            throw new Exception("切片参数不齐全");
        }
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILE_SRC_PATH + "/" + fileName;
        AbstractSlicer abstractSlicer = new PDGSlicer(path);
        return new StringDto(abstractSlicer.slice(lineNumber,variable).getResultCode());
    }

    @GetMapping("/dynamic")
    @ApiOperation(value = "动态切片")
    public StringDto DynamicSlicing(String id, int lineNumber, String input) throws Exception{
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILE_SRC_PATH + "/" + fileName;
        DynamicSlicer dynamicSlicer = new DynamicSlicer(path);
        return new StringDto(dynamicSlicer.slice(input, lineNumber).getSlicedCode());
    }

}
