package com.nju.boot.controller;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.XmlPrinter;
import com.nju.boot.entity.dto.MethodDto;
import com.nju.boot.entity.dto.StringDto;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.util.GraphsUtil;
import com.nju.boot.util.JsonDataModifier;
import com.nju.boot.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/save")
@Api(tags = "获取生成图的图片路径")
public class SaveController {

    @Resource
    private FilesMapper filesMapper;

    @GetMapping("/ast")
    @ApiOperation(value = "获得语法分析树AST图片")
    public StringDto getASTPath(@RequestParam("id") String id) throws Exception{
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILE_SRC_PATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        CompilationUnit compilationUnit = graphs.getCu();
        File uploadParentFile = new File(PathUtils.FILE_AST_PATH);
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        String savePath = PathUtils.FILE_AST_PATH + "/" + fileName;
        GraphsUtil.astNodeToPNGOutput(compilationUnit, savePath);
        return new StringDto(savePath);
    }

    @GetMapping("/cg")
    @ApiOperation(value = "获得函数调用图CG图片")
    public StringDto getCGPath(@RequestParam("id") String id) throws Exception{
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILE_SRC_PATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        File uploadParentFile = new File(PathUtils.FILE_CG_PATH);
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        String savePath = PathUtils.FILE_CG_PATH + "/" + fileName;
        graphs.getCallGraph().save2FileAsPNG(savePath);
        return new StringDto(savePath);
    }

    @GetMapping("/cfg")
    @ApiOperation(value = "获得控制流图CFG图片")
    public StringDto getCFGPath(@RequestParam("id") String id) throws Exception{
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILE_SRC_PATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        String method = graphs.getQualifiedSignatures().stream().toList().get(0);
        File uploadParentFile = new File(PathUtils.FILE_CFG_PATH);
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        String savePath = PathUtils.FILE_CFG_PATH + "/" + fileName;
        graphs.getCFG(method).save2FileAsPNG(savePath);
        return new StringDto(savePath);
    }

    @GetMapping("/pdg")
    @ApiOperation(value = "获得控制流图CFG图片")
    public StringDto getPDGPath(@RequestParam("id") String id) throws Exception{
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILE_SRC_PATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        String method = graphs.getQualifiedSignatures().stream().toList().get(0);
        File uploadParentFile = new File(PathUtils.FILE_PDG_PATH);
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        String savePath = PathUtils.FILE_PDG_PATH + "/" + fileName;
        graphs.getPDG(method).save2FileAsPNG(savePath);
        return new StringDto(savePath);
    }

}
