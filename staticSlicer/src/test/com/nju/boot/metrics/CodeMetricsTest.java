package com.nju.boot.metrics;

import com.nju.boot.graphs.Graphs;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

class CodeMetricsTest {
    static String absolutePath = new File("").getAbsolutePath();

    static String inheritedClassesPath = Paths.get(absolutePath,"data","testcases", "codeMetrics","InheritedClasses.java").toString();
    static String lineOfCodeTestPath = Paths.get(absolutePath,"data","testcases", "codeMetrics","LineOfCodeTest.java").toString();
    static String cycCompTestPath = Paths.get(absolutePath,"data","testcases", "codeMetrics","CycCompTest.java").toString();

    @Test
    void getMaxDepthOfInheritance() {
        Graphs graphs = new Graphs(inheritedClassesPath);
        CodeMetrics codeMetrics = new CodeMetrics(graphs);
        System.out.println("the depth of Inheritance is "+
                codeMetrics.getMaxDepthOfInheritance());

    }

    @Test
    void getLinesOfCode() {//todo：修正
        Graphs graphs = new Graphs(lineOfCodeTestPath);
        CodeMetrics codeMetrics = new CodeMetrics(graphs);
        String methodSig = graphs.getQualifiedSignatures().stream().collect(Collectors.toList()).get(0);
        Assertions.assertEquals(10,codeMetrics.getLinesCodeOfMethod(methodSig));
        Assertions.assertEquals(6,codeMetrics.getLinesCommentOfMethod(methodSig));
        Assertions.assertEquals(0,codeMetrics.getLinesBlankOfMethod(methodSig));

    }

    @Test
    void getCyclomaticComplexity() {
        Graphs graphs = new Graphs(cycCompTestPath);
        CodeMetrics codeMetrics = new CodeMetrics(graphs);
        Set<Integer> results = new HashSet<>() ;
        for(String methodSig : graphs.getQualifiedSignatures()){
            results.add(codeMetrics.getCyclomaticComplexity(methodSig));
        }
        Set<Integer>expected = new HashSet<>();
        expected.add(5);
        Assertions.assertArrayEquals(expected.toArray(),results.toArray());

    }
}
