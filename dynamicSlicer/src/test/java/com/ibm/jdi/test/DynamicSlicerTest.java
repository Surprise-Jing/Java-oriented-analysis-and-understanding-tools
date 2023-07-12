package com.ibm.jdi.test;

import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.util.GraphsUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;

class DynamicSlicerTest {
    String absolutePath = new File("").getAbsolutePath();
    String filePath = Paths.get(absolutePath, "data", "test", "src").toString();
    String fileName = "Test1.java";
    String wholePath = Paths.get(filePath, fileName).toString();

    @Test
    void programExecute() throws Exception {
        Graphs graphs = new Graphs(wholePath);
        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(), 20));
        System.out.println(cdg.toString());
        Set<Integer> result = new DynamicSlicer().ProgramExecute(filePath, fileName, "Test1", "", 20, cdg);
        System.out.println(result);
    }
}
