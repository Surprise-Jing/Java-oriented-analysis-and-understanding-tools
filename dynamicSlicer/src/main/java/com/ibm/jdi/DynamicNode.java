package com.ibm.jdi;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DynamicNode {

    private int lineNumber;

    private Set<DynamicNode> Def;

    private Set<DynamicNode> Pred;

    private Set<Integer> ReachableStmt;

    DynamicNode(int ln, Set<DynamicNode> def, Set<DynamicNode> pred, Set<Integer> reachableStmt){
        this.lineNumber = ln;
        this.Def = new HashSet<>(def);
        this.Pred = new HashSet<>(pred);
        this.ReachableStmt = new HashSet<>(reachableStmt);
    }
    public boolean sameDef(Set<DynamicNode> D){
        return Def.equals(D);
    }

    public boolean samePred(Set<DynamicNode> C){
        return Pred.equals(C);
    }
}
