package com.nju.boot.graphs.dependencegraph;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

import java.util.Set;

public class ControlDependencyBuilder {
    DependenceGraph pdg;
    ACFG acfg;
    DominatorTree postDominatorTree;


    public ControlDependencyBuilder(DependenceGraph pdg) {
        this.pdg = pdg;
        this.acfg = pdg.acfg;
        //通过acfg图得到后向支配树
        //在后向支配树中，父结点后向支配其子节点
        postDominatorTree = new DominatorTree(acfg.reverse());
        postDominatorTree.build();
    }
    /**
     * @return if a post-dominates b*/
    public boolean postDominates(GraphNode<?>a,GraphNode<?>b){
        return postDominatorTree.dominates(a,b);
    }


    public void build(){
        //对于acfg图中的每一条边src->target
        for(Edge controlFlowEdge: acfg.edgeSet()){
            GraphNode<?> src = acfg.getEdgeSource(controlFlowEdge),
                    target = acfg.getEdgeTarget(controlFlowEdge);


            if(!postDominatorTree.dominates(target,src)){
                GraphNode<?>n = target;
                //n的初始值为target
                //如果n不是src的后支配节点
                //即从src到exit，有一条不经过n的路径
                //则n控制依赖于src
                while(!postDominatorTree.dominates(n,src)){
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
