package com.nju.boot.graphs.dependencegraph;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.graphs.augmented.ACFG;
import org.checkerframework.checker.units.qual.A;

public class CDG extends DependenceGraph{

    public void buildFromACFG(ACFG acfg){
        if(built)return;
        built = true;
        this.acfg = acfg;
        acfg.vertexSet().stream().filter(vertex->vertex!=acfg.getExitNode().get()).forEach(this::addVertex);
        buildControlDependency(this);
    }
    public void build(CallableDeclaration<?>callableDeclaration){
        if(built)return;
        acfg = new ACFG();
        acfg.build(callableDeclaration);
        buildFromACFG(acfg);
    }
}
