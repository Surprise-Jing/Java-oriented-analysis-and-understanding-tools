package com.ibm.jdi;

import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.util.GraphsUtil;
import io.swagger.models.auth.In;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DynamicSlicer {
    Set<Integer> result;
    Graphs graphs;
    String path;
    String fileName;
    String className;

    public DynamicSlicer(String filePath) {
        System.out.println(filePath);
        path = Paths.get(filePath).getParent().toString();
        System.out.println(path);
        fileName = Paths.get(filePath).getFileName().toString();
        System.out.println(fileName);
        className = fileName.substring(0, fileName.lastIndexOf('.'));
        System.out.println(className);
        graphs = new Graphs(filePath);
    }

    public Set<Integer> programExecute(String path, String fileName, String className, String input, Integer line, CDG cdg) throws Exception {
        DynamicExecuter dynamicExecuter = new DynamicExecuter();

        Map<Integer, Integer> labels = new HashMap<>();
        for(GraphNode<?> GN : cdg.vertexSet()) {
            if(GN.getAstNode() instanceof LabeledStmt || GN.getAstNode() instanceof SwitchEntry) {
                Integer label = GN.getAstNode().getBegin().get().line;
                int closest = Integer.MAX_VALUE;
                for (Edge edge : cdg.outgoingEdgesOf(GN)) {
                    closest = Integer.min(closest, cdg.getEdgeTarget(edge).getAstNode().getBegin().get().line);
                }
                if(closest != label) {
                    labels.put(closest, label);
                }
            }
        }
//        System.out.println(labels);
        dynamicExecuter.setLinesOfLabels(labels);
        boolean bld = dynamicExecuter.executeFile(path, fileName, className, input);
        if (bld)
            dynamicExecuter.buildingDDG(cdg);
        else
            return null;
        result = dynamicExecuter.dynamicSlice(line);
        return result;
        //return dynamicExecuter.dynamicSlice(line);
    }

    public DynamicSlicer slice(String input, int line) throws Exception {
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
