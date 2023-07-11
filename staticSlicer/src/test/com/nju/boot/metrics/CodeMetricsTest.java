package com.nju.boot.metrics;

import com.nju.boot.metrics.CodeMetrics;
import com.nju.boot.slicer.Graphs;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

class CodeMetricsTest {
    static String absolutePath = new File("").getAbsolutePath();
    static String inheritedClassesPath = Paths.get(absolutePath,"data","testcases", "codeMetrics","InheritedClasses.java").toString();
    static String lineOfCodeTestPath = Paths.get(absolutePath,"data","testcases", "codeMetrics","LineOfCodeTest.java").toString();
    @Test
    void getMaxDepthOfInheritance() {
        Graphs graphs = new Graphs(inheritedClassesPath);
        CodeMetrics codeMetrics = new CodeMetrics(graphs);
        System.out.println("the depth of Inheritance is "+
                codeMetrics.getMaxDepthOfInheritance());

    }

    @Test
    void getLineOfMethod() {
        Graphs graphs = new Graphs(lineOfCodeTestPath);
        CodeMetrics codeMetrics = new CodeMetrics(graphs);
        String methodName = graphs.getQualifiedSignatures().stream().toList().get(0);
        System.out.println(codeMetrics.getLineOfMethod(methodName));
    }
}
