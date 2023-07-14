package com.nju.boot.controller;

import com.nju.boot.entity.dto.LinesDto;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.metrics.CodeMetrics;
import com.nju.boot.util.GraphsUtil;
import com.nju.boot.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/metrics")
@Api(tags = "代码度量接口")
public class MetricsController {

    @Resource
    private FilesMapper filesMapper;

    @GetMapping("/cyc")
    @ApiOperation(value = "计算函数的圈复杂度")
    public int CyclomaticComplexity(String id, String method){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getCyclomaticComplexity(method);
    }

    @GetMapping("/depth")
    @ApiOperation(value = "计算所有类的最大继承深度")
    public int MaxDepthOfInheritance(String id){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getMaxDepthOfInheritance();
    }

    @GetMapping("/calling")
    @ApiOperation(value = "计算函数的调用次数")
    public int TimesCalling(String id, String method){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getTimesCalling(method);
    }

    @GetMapping("/called")
    @ApiOperation(value = "计算函数的被调用次数")
    public int TimesCalled(String id, String method){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getTimesCalled(method);
    }

    @GetMapping("/param")
    @ApiOperation(value = "计算函数的入参个数")
    public int NumOfParameters(String id, String method){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getNumOfParameters(method);
    }

    @GetMapping("/methodlines")
    @ApiOperation(value = "统计文件中函数的行数信息")
    public LinesDto LinesMethod(String id, String method){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        int linesOfCode = codeMetrics.getLinesCodeOfMethod(method);
        int linesOfComment = codeMetrics.getLinesCommentOfMethod(method);
        int linesOfBlanks = codeMetrics.getLinesBlankOfMethod(method);
        int linesOfAll = linesOfBlanks + linesOfComment + linesOfCode;
        return new LinesDto(linesOfCode, linesOfComment, linesOfBlanks, linesOfAll);
    }

    @GetMapping("/lines")
    @ApiOperation(value = "统计文件的行数信息")
    public LinesDto Lines(String id){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        int linesOfCode = codeMetrics.getLinesCode();
        int linesOfComment = codeMetrics.getLinesComment();
        int linesOfBlanks = codeMetrics.getLinesBlank();
        int linesOfAll = linesOfBlanks + linesOfComment + linesOfCode;
        return new LinesDto(linesOfCode, linesOfComment, linesOfBlanks, linesOfAll);
    }



}
