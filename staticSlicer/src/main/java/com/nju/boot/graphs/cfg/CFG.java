package com.nju.boot.graphs.cfg;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.ControlFlowEdge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.nodes.GraphNode;

public class CFG extends Graph<MethodDeclaration> {
    protected boolean built = false;

    public CFG(){
        super();
    }

    public void addControlFlowEdge(GraphNode<?> from, GraphNode<?> to, ControlFlowEdge edge){
        super.addEdge(from, to, edge);
    }

    public void addControlFlowEdge(GraphNode<?> from, GraphNode<?> to){
        addControlFlowEdge(from, to, new ControlFlowEdge());
    }

    public void build(MethodDeclaration methodDeclaration){
        methodDeclaration.accept(new CFGBuilder(this), null);
        built = true;
    }

}
