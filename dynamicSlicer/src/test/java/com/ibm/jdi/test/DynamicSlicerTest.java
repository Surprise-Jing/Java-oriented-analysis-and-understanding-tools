package com.ibm.jdi.test;

import com.ibm.jdi.DynamicSlicer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

class DynamicSlicerTest {
    String absolutePath = new File("").getAbsolutePath();
    String filePath = Paths.get(absolutePath,"data","test","src").toString();
    String fileName = "WhileIf2.java";
    String wholePath = Paths.get(filePath,fileName).toString();
    @Test
    void ForTest() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 18;
        String input = "10";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "For.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }

    @Test
    void ForEachTest() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 14;
        String input = "";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "ForEach.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }

    @Test
    void IfElseTest() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 17;
        String input = "";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "IfElse.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
    @Test
    void LongLoopTest() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 15;
        String input = "2";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "LongLoop.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
    @Test
    void NormalTest() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 10;
        String input = "";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "NormalTest.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
    @Test
    void SwtichTest() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 29;
        String input = "1";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "Switch.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
    @Test
    void WhileBreakTest() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 21;
        String input = "";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "WhileBreak.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
    @Test
    void WhileContinueTest() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 15;
        String input = "0";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "WhileContinue.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
    @Test
    void WhileIf() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 21;
        String input = "2 -1 1";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "WhileIf.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
    @Test
    void WhileIf2() throws Exception {
//        Graphs graphs = new Graphs(wholePath);
        int lineNumber = 18;
        String input = "2";
//        CDG cdg = graphs.getCDG(GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber));
//        System.out.println(cdg.toString());
        String result = new DynamicSlicer(filePath + "\\" + "WhileIf2.java").slice(input ,lineNumber).getSlicedCode();
        System.out.println("\n" + result);
    }
}
