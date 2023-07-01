package com.nju.boot.graphs.pdg;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

import java.util.Set;

public class ControlDependencyBuilder {
    PDG pdg;
    CFG cfg;

    public ControlDependencyBuilder(PDG pdg) {
        this.pdg = pdg;
        this.cfg = pdg.cfg;
    }
    public void build(){

    }
    public Set<GraphNode<?>>getLatestPostDominator(GraphNode<?> node){
        return null;
    }

}
