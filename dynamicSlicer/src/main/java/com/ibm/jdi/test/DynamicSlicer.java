package com.ibm.jdi.test;

import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.graphs.dependencegraph.PDG;

import java.util.HashSet;
import java.util.Set;

public class DynamicSlicer {
    public Set<Integer> ProgramExecute(String path, String fileName, String className, String input, Integer line, CDG cdg) throws Exception {
        DynamicExecuter dynamicExecuter = new DynamicExecuter();
        boolean bld = dynamicExecuter.executeFile(path, fileName, className, input);
        if (bld)
            dynamicExecuter.buildingDDG(cdg);
        else
            return null;
        return dynamicExecuter.dynamicSlice(line);
    }
}
