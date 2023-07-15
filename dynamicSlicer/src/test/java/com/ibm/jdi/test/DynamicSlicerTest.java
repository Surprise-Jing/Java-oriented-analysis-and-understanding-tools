package com.ibm.jdi.test;

import com.github.javaparser.ast.Node;
import com.ibm.jdi.DynamicSlicer;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.util.GraphsUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;

class DynamicSlicerTest {
    String absolutePath = new File("").getAbsolutePath();
    String filePath = Paths.get(absolutePath,"data","test","src").toString();
    String fileName = "Test8.java";
    String wholePath = Paths.get(filePath,fileName).toString();
    @Test
    void programExecute() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 14;
        String input = "3";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + fileName).slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
}
