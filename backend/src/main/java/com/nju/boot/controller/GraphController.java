package com.nju.boot.controller;

import com.github.javaparser.ast.CompilationUnit;
import com.nju.boot.slicer.Graphs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graph")
@Api(tags = "Java文件解析成代码抽象模型接口")
public class GraphController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @GetMapping("/ast")
    @ApiOperation(value = "获得语法分析树AST")
    public String getAST(@RequestParam("id") String id){
        String path = fileUploadPath + "/" + id;
        Graphs graphs = new Graphs(path);
        return graphs.getCu().toString();
    }

    @GetMapping("/cg")
    @ApiOperation(value = "获得函数调用图Call graph")
    public String getCallGraph(@RequestParam("id") String id){
        String path = fileUploadPath + "/" + id;
        Graphs graphs = new Graphs(path);
        return graphs.getCallGraph().toString();
    }

    @GetMapping("/method")
    @ApiOperation(value = "获得文件的所有方法")
    public String getMethod(@RequestParam("id") String id){
        return "";
    }
}
