package com.nju.boot.graphs.pdg;

import com.github.javaparser.ast.Node;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

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

        }
    }

    //todo: change the returned value type after finishing implementing
    public void getUsedVariables(GraphNode<?>node){
        Node astNode = node.getAstNode();

    }
    public void getDefinedVariables(GraphNode<?>node){

        Node astNode = node.getAstNode();
    }


}
