package com.nju.boot.controller;

import com.nju.boot.entity.dto.MethodDto;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.util.GraphsUtil;
import com.nju.boot.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/graph")
@Api(tags = "Java文件解析成代码抽象模型接口")
public class GraphController {

    @Resource
    private FilesMapper filesMapper;


    @GetMapping("/ast")
    @ApiOperation(value = "获得语法分析树AST")
    public String getAST(@RequestParam("id") String id){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        return GraphsUtil.astNodeToXml(graphs.getCu());
    }

    @GetMapping("/cg")
    @ApiOperation(value = "获得函数调用图Call graph")
    public String getCallGraph(@RequestParam("id") String id){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        return graphs.getCallGraph().toString();
    }

    @GetMapping("/method")
    @ApiOperation(value = "获得文件的所有方法")
    public List<MethodDto> getMethod(@RequestParam("id") String id){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        List<String> method = graphs.getQualifiedSignatures().stream().toList();
        List<MethodDto> methodDtos = new LinkedList<>();
        int methodId = 1;
        for(String m: method){
            methodDtos.add(new MethodDto(methodId++, m));
        }
        return methodDtos;
    }

    @GetMapping("/cfg")
    @ApiOperation(value = "获得控制流程图CFG")
    public String getCFG(String id, String method){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        if(method == null){
            Set<String> methods = graphs.getQualifiedSignatures();
            method = methods.stream().toList().get(0);
        }
        return graphs.getCFG(GraphsUtil.findMethodBySignature(graphs, method)).toString();
    }

    @GetMapping("/pdg")
    @ApiOperation(value = "获得程序依赖图PDG")
    public String getPDG(String id, String method){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        if(method == null){
            Set<String> methods = graphs.getQualifiedSignatures();
            method = methods.stream().toList().get(0);
        }
        return graphs.getPDG(GraphsUtil.findMethodBySignature(graphs, method)).toString();
    }
}
