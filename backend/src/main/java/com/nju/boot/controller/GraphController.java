package com.nju.boot.controller;

import cn.hutool.json.JSON;
import com.github.javaparser.printer.XmlPrinter;
import com.nju.boot.entity.dto.MethodDto;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.mapper.FilesMapper;
import com.nju.boot.util.GraphsUtil;
import com.nju.boot.util.JsonDataModifier;
import com.nju.boot.utils.PathUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/graph")
@Api(tags = "Java文件解析成代码抽象模型接口")
public class GraphController {

    @Resource
    private FilesMapper filesMapper;


    @GetMapping("/ast")
    @ApiOperation(value = "获得语法分析树AST")
    public Map<String, String> getAST(@RequestParam("id") String id){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        JsonDataModifier jsonDataModifier = new JsonDataModifier(new XmlPrinter(true).output(graphs.getCu()));
        Map<String, String> res = new HashMap<>();
        jsonDataModifier.modify();
        res.put("result", jsonDataModifier.getResult().toString());
        return res;
    }

    @GetMapping("/cg")
    @ApiOperation(value = "获得函数调用图Call graph")
    public Map<String, String> getCallGraph(@RequestParam("id") String id){
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        Map<String, String> res = new HashMap<>();
        res.put("result", graphs.getCallGraph().toString());
        return res;
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

    @PostMapping("/cfg")
    @ApiOperation(value = "获得控制流程图CFG")
    public Map<String, String> getCFG(@RequestParam("id") String id, @RequestBody MethodDto methodDto){
        String func = methodDto.getMethodName();
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        if(func == null){
            Set<String> methods = graphs.getQualifiedSignatures();
            func = methods.stream().toList().get(0);
        }
        Map<String, String> res = new HashMap<>();
        res.put("result", graphs.getCFG(GraphsUtil.findMethodBySignature(graphs, func)).toString());
        return res;
    }

    @PostMapping("/pdg")
    @ApiOperation(value = "获得程序依赖图PDG")
    public Map<String, String> getPDG(@RequestParam("id") String id, @RequestBody MethodDto methodDto){
        String func = methodDto.getMethodName();
        String fileName = filesMapper.selectById(id).getName();
        String path = PathUtils.FILEPATH + "/" + fileName;
        Graphs graphs = new Graphs(path);
        if(func == null){
            Set<String> methods = graphs.getQualifiedSignatures();
            func = methods.stream().toList().get(0);
        }
        Map<String, String> res = new HashMap<>();
        res.put("result", graphs.getPDG(GraphsUtil.findMethodBySignature(graphs, func)).toString());
        return res;
    }
}
