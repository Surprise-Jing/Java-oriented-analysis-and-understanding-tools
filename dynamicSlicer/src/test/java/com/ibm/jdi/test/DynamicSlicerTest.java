package com.ibm.jdi.test;

import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.slicer.Graphs;
import com.nju.boot.util.SlicerUtil;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DynamicSlicerTest {
    String absolutePath = new File("").getAbsolutePath();
    String filePath = Paths.get(absolutePath,"data","test","src").toString();
    String fileName = "Test1.java";
    String wholePath = Paths.get(filePath,fileName).toString();
    @Test
    void programExecute() throws Exception {
        Graphs graphs = new Graphs(wholePath);

        CDG cdg  = graphs.getCDG(SlicerUtil.findMethodByLineNumber(graphs.getCu(),20));
        System.out.println(cdg.toString());
        Set<Integer> result = new DynamicSlicer().ProgramExecute(filePath,fileName,"Test1","",20,cdg);
        System.out.println(result);
    }
}
