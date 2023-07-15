package com.ibm.jdi;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.util.GraphsUtil;
import io.swagger.models.auth.In;

import java.io.*;
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
            if(GN.getAstNode() instanceof LabeledStmt || GN.getAstNode() instanceof SwitchEntry || GN.getAstNode() instanceof ForStmt || GN.getAstNode() instanceof MethodDeclaration) {
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
//        System.out.println("labels: " + labels);
        dynamicExecuter.setLinesOfLabels(labels);
        boolean bld = dynamicExecuter.executeFile(path, fileName, className, input);
        if (bld)
            dynamicExecuter.buildingDDG(cdg);
        else
            return null;
        result = dynamicExecuter.dynamicSlice(line);

        System.out.println(getSlicedCode());

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
    public String getSlicedCode() throws IOException {
//        String fileStr = graphs.getCu().toString();
        StringBuilder fileStrBuilder = new StringBuilder();
        File file = new File(path + "\\" + fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        List<String> lines = new ArrayList<>();

        String s = null;
        while ((s = bufferedReader.readLine()) != null) {
//            fileStrBuilder.append(System.lineSeparator()).append(s);
            lines.add(s);
        }
        bufferedReader.close();

//        String fileStr = fileStrBuilder.toString();


        //test
/*        for(String l : lines) {
            System.out.println(l);
        }*/

        StringBuilder resultStr = new StringBuilder();
        for(int i = 0;i<lines.size();i++){
            if(result.contains(i+1)){
                resultStr.append(System.lineSeparator()).append(lines.get(i));
            }

        }
        return resultStr.toString();
    }
}
