package com.nju.boot.metrics;

import com.nju.boot.graphs.Graphs;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
        List<Integer>lOCs =  graphs.getQualifiedSignatures().stream()
                .map(codeMetrics::getLineOfMethod).collect(Collectors.toList());
        assert lOCs.equals(Arrays.asList(new Integer[]{10}));

    }
}
