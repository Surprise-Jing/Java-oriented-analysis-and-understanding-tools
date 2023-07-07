package com.nju.boot.graphs.augmented;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.DummyEdge;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;


/** A jump vertex’s true-successor is the target of the jump;
 * its false-successor is the vertex that represents the jump statement’s fall-through or continuation */
public class ACFG extends CFG {
    public void addDummyEdge(GraphNode<?> from, GraphNode<?> to, DummyEdge edge){
        addEdge(from,to,edge);
    }
    public void addDummyEdge(GraphNode<?> from, GraphNode<?> to){
        addDummyEdge(from, to, new DummyEdge());
    }

    @Override
    public void build(MethodDeclaration methodDeclaration) {
        built = true;
        methodDeclaration.accept(new ACFGBuilder(this),null);
        addDummyEdge(this.root,this.exit,new DummyEdge());
    }
}
