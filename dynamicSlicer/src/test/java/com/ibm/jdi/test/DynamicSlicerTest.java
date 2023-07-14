package com.ibm.jdi.test;

import com.ibm.jdi.DynamicSlicer;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.util.GraphsUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;

class DynamicSlicerTest {
    //String absolutePath = new File("").getAbsolutePath();
    //String filePath = Paths.get(absolutePath,"data","test","src").toString();
    String filePath = "D://Java-oriented-analysis-and-understanding-tools//backend//file";
    String fileName = "8fe35e5c-3e81-4107-a6cc-4e7ad95883bb.java";
    String wholePath = Paths.get(filePath,fileName).toString();
    @Test
    void programExecute() throws Exception {
        Graphs graphs = new Graphs(wholePath);

        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),18));
//        System.out.println(cdg.toString());
        //String className = fileName.substring(0, fileName.indexOf("."));
        String className = "Test6";
        Set<Integer> result = new DynamicSlicer(filePath + "\\" + fileName, className).slice("",18).getSlicedLines();
        System.out.println("\n" + result);
    }
}
