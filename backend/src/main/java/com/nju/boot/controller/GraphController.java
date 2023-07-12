package com.nju.boot.controller;

import com.nju.boot.handler.DisableBaseResponse;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.util.GraphsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/graph")
@Api(tags = "Java文件解析成代码抽象模型接口")
public class GraphController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @DisableBaseResponse
    @GetMapping("/ast")
    @ApiOperation(value = "获得语法分析树AST")
    public String getAST(@RequestParam("id") String id){
        String path = fileUploadPath + "/" + id;
        Graphs graphs = new Graphs(path);
        return GraphsUtil.astNodeToXml(graphs.getCu());
    }

    @DisableBaseResponse
    @GetMapping("/cg")
    @ApiOperation(value = "获得函数调用图Call graph")
    public String getCallGraph(@RequestParam("id") String id){
        String path = fileUploadPath + "/" + id;
        Graphs graphs = new Graphs(path);
        return graphs.getCallGraph().toString();
    }

    @DisableBaseResponse
    @GetMapping("/method")
    @ApiOperation(value = "获得文件的所有方法")
    public Set<String> getMethod(@RequestParam("id") String id){
        String path = fileUploadPath + "/" + id;
        Graphs graphs = new Graphs(path);
        return graphs.getQualifiedSignatures();
    }

    @DisableBaseResponse
    @GetMapping("/cfg")
    @ApiOperation(value = "获得控制流程图CFG")
    public String getCFG(String id, String method){
        String path = fileUploadPath + "/" + id;
        Graphs graphs = new Graphs(path);
        if(method == null){
            Set<String> methods = graphs.getQualifiedSignatures();
            method = methods.stream().toList().get(0);
        }
        return graphs.getCFG(GraphsUtil.findMethodBySignature(graphs, method)).toString();
    }

    @DisableBaseResponse
    @GetMapping("/pdg")
    @ApiOperation(value = "获得程序依赖图PDG")
    public String getPDG(String id, String method){
        String path = fileUploadPath + "/" + id;
        Graphs graphs = new Graphs(path);
        if(method == null){
            Set<String> methods = graphs.getQualifiedSignatures();
            method = methods.stream().toList().get(0);
        }
        return graphs.getPDG(GraphsUtil.findMethodBySignature(graphs, method)).toString();
    }
}
