package com.ibm.jdi.test;

import com.ibm.jdi.DynamicSlicer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

class DynamicSlicerTest {
    String absolutePath = new File("").getAbsolutePath();
    String filePath = Paths.get(absolutePath,"data","test","src").toString();
    String fileName = "Test2.java";
    String wholePath = Paths.get(filePath,fileName).toString();
    @Test
    void programExecute() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 21;
        String input = "2 1 1";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + fileName).slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
}
