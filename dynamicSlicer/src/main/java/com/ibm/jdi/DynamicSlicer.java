package com.ibm.jdi;

import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.util.GraphsUtil;

import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DynamicSlicer {
    Set<Integer> result;
    Graphs graphs;
    String path;
    String fileName;
    String className;

    public DynamicSlicer(String filePath) {
        path = Paths.get(filePath).getParent().toString();
        fileName = Paths.get(filePath).getFileName().toString();
        className = fileName.substring(0, fileName.lastIndexOf('.'));
        graphs = new Graphs(filePath);
    }

    public Set<Integer> programExecute(String path, String fileName, String className, String input, Integer line, CDG cdg) throws Exception {
        DynamicExecuter dynamicExecuter = new DynamicExecuter();
        boolean bld = dynamicExecuter.executeFile(path, fileName, className, input);
        if (bld)
            dynamicExecuter.buildingDDG(cdg);
        else
            return null;
        result = dynamicExecuter.dynamicSlice(line);
        return result;
        //return dynamicExecuter.dynamicSlice(line);
    }

    public DynamicSlicer slice( String input, int line) throws Exception {
        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(), line));
        result = programExecute(path, fileName, className, input, line, cdg);
        return this;
    }
    public Set<Integer> getSlicedLines(){
        return result;
    }
    public String getSlicedCode() {
        String fileStr = graphs.getCu().toString();
        List<String> lines = fileStr.lines().collect(Collectors.toList());
        String resultStr = new String();
        for(int i = 0;i<lines.size();i++){
            if(result.contains(i+1))resultStr+=lines.get(i);
        }
        return resultStr;
    }
}
