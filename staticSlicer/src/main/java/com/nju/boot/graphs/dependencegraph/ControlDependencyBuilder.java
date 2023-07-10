package com.nju.boot.graphs.dependencegraph;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

import java.util.Set;

public class ControlDependencyBuilder {
    DependenceGraph pdg;
    CFG cfg;

    public ControlDependencyBuilder(DependenceGraph pdg) {
        this.pdg = pdg;
        this.cfg = pdg.acfg;
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
                    if(!src.equals(n))
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
