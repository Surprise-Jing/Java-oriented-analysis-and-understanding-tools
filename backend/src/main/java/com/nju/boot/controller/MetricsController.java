package com.nju.boot.controller;

import com.nju.boot.entity.dto.LinesDto;
import com.nju.boot.entity.dto.MethodDto;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.metrics.CodeMetrics;
import com.nju.boot.util.GraphsUtil;
import com.nju.boot.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/metrics")
@Api(tags = "代码度量接口")
public class MetricsController {

    @Resource
    private FilesMapper filesMapper;

    @PostMapping("")
    @ApiOperation(value = "统计函数的所有基本度量信息")
    public Map<String, Integer> getMetrics(@RequestParam("id") String id, @RequestBody MethodDto methodDto){
        String func = methodDto.getMethodName();
        //System.out.println(func);
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        Map<String, Integer> map = new HashMap<>();
        map.put("cyclomatic", codeMetrics.getCyclomaticComplexity(func));
        map.put("maxdepth", codeMetrics.getMaxDepthOfInheritance());
        map.put("calling", codeMetrics.getTimesCalling(func));
        map.put("called", codeMetrics.getTimesCalled(func));
        map.put("param", codeMetrics.getNumOfParameters(func));
        return map;
    }

    @PostMapping("/cyc")
    @ApiOperation(value = "计算函数的圈复杂度")
    public int CyclomaticComplexity(@RequestParam("id") String id, @RequestBody MethodDto methodDto){
        String func = methodDto.getMethodName();
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getCyclomaticComplexity(func);
    }

    @GetMapping("/depth")
    @ApiOperation(value = "计算所有类的最大继承深度")
    public int MaxDepthOfInheritance(String id){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getMaxDepthOfInheritance();
    }

    @PostMapping("/calling")
    @ApiOperation(value = "计算函数的调用次数")
    public int TimesCalling(@RequestParam("id") String id, @RequestBody MethodDto methodDto){
        String func = methodDto.getMethodName();
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getTimesCalling(func);
    }

    @PostMapping("/called")
    @ApiOperation(value = "计算函数的被调用次数")
    public int TimesCalled(@RequestParam("id") String id, @RequestBody MethodDto methodDto){
        String func = methodDto.getMethodName();
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getTimesCalled(func);
    }

    @PostMapping("/param")
    @ApiOperation(value = "计算函数的入参个数")
    public int NumOfParameters(@RequestParam("id") String id, @RequestBody MethodDto methodDto){
        String func = methodDto.getMethodName();
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        return codeMetrics.getNumOfParameters(func);
    }

    @PostMapping("/methodlines")
    @ApiOperation(value = "统计文件中函数的行数信息")
    public LinesDto LinesMethod(@RequestParam("id") String id, @RequestBody MethodDto methodDto){
        String func = methodDto.getMethodName();
        System.out.println(func);
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        CodeMetrics codeMetrics = new CodeMetrics(path);
        int linesOfCode = codeMetrics.getLinesCodeOfMethod(func);
        int linesOfComment = codeMetrics.getLinesCommentOfMethod(func);
        int linesOfBlanks = codeMetrics.getLinesBlankOfMethod(func);
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
