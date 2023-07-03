package com.nju.boot.graphs.pdg;

import com.github.javaparser.ast.Node;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.DummyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.Set;

public class DataDependencyBuilder {
    PDG pdg;
    ACFG acfg;
    public DataDependencyBuilder(PDG pdg) {
        this.pdg = pdg;
        this.acfg = pdg.cfg;
    }
    /**add data dependency edge to the pdg*/
    public void build(){
        //Let v and w be vertices in a CFG.
        // There is a flow dependence from vertex v to vertex w (written v →f w)
        // iff vertex v assigns to variable x, vertex w uses x,
        // and there is a path from v to w that does not include an assignment to x
        // (excluding v and w)
        // and that does not include any “dummy” edge.
        for(GraphNode<?> node:acfg.vertexSet()){
            Set<String> usedVariables = node.getUsedVariables() ;

            //do not traverse through dummy edge
            acfg.incomingEdgesOf(node).stream()
                    .filter(edge -> !(edge instanceof DummyEdge))
                    .map(edge -> acfg.getEdgeSource(edge))
                    .forEach(srcNode->backwardTraverse(node,srcNode,new HashSet<>(usedVariables)));

        }
    }
    public void backwardTraverse(GraphNode<?>src,GraphNode<?>target,Set<String>usedVariables){
        //stop when entry is met
        if(target == acfg.getRootNode().get())return;
        //stop when usedVariables becomes empty
        else if (usedVariables.isEmpty())return;
        Set<String> definedVariables = target.getDefinedVariables();
        for (String variable:definedVariables){
            if(usedVariables.contains(variable)){
                //the src node is data-dependent on target
                //未考虑两个节点之间同时存在同方向的数据依赖和控制依赖的情况
                Edge e = pdg.getEdge(target,src);
                if(e!=null){
                    assert e instanceof DataDependencyEdge;
                    ((DataDependencyEdge) e).addDependentVariable(variable);
                }
                else{
                    e = pdg.addDataDependencyEdge(target,src);
                    ((DataDependencyEdge) e).addDependentVariable(variable);
                }


                //delete the killed variable from the usedVariable set
                usedVariables.remove(variable);
            }
        }
        acfg.incomingEdgesOf(target).stream()
                .filter(edge -> !(edge instanceof DummyEdge))
                .map(acfg::getEdgeSource)
                .forEach(srcNode->backwardTraverse(src,srcNode,new HashSet<>(usedVariables)));

    }



}
