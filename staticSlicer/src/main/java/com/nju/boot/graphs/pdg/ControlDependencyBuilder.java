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
        DominatorTree postDominatorTree = new DominatorTree(cfg.reverse());
        postDominatorTree.build();
        for(Edge controlFlowEdge:cfg.edgeSet()){
            GraphNode<?> src = cfg.getEdgeSource(controlFlowEdge),
                    target = cfg.getEdgeTarget(controlFlowEdge);
            if(postDominatorTree.dominates(target,src)){
                continue;
            }
            else{
                GraphNode<?>n = target;
                while(!postDominatorTree.dominates(n,src)){
                    //n is control dependent on src
                    pdg.addControlDependencyEdge(src,n);
                    n = postDominatorTree.getParent(n);
                }
            }
        }
    }
    public Set<GraphNode<?>>getLatestPostDominator(GraphNode<?> node){
        return null;
    }

}
