package com.ibm.jdi.test;

import com.nju.boot.graphs.pdg.PDG;

import java.util.Set;

public class DynamicSlicer {
    public Set<Integer> ProgramExecute(String path, String fileName, String className, String input, Integer line, PDG cdg) throws Exception {
        DynamicExecuter dynamicExecuter = new DynamicExecuter();
        dynamicExecuter.executeFile(path, fileName, className, input);
        dynamicExecuter.buildingDDG(cdg);
        return dynamicExecuter.dynamicSlice(line);
    }
}
