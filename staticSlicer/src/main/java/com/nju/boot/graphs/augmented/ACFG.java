package com.nju.boot.graphs.augmented;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.DummyEdge;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;


/** A.java jump vertex’s true-successor is the target of the jump;
 * its false-successor is the vertex that represents the jump statement’s fall-through or continuation
 * 添加了DummyEdge的CFG，将break、continue等跳转节点当作伪判断节点，
 * 其F边指向的节点为其操作是Noop是程序的控制流流向的节点
 * * */
public class ACFG extends CFG {
    public void addDummyEdge(GraphNode<?> from, GraphNode<?> to, DummyEdge edge){
        addEdge(from,to,edge);
    }
    public void addDummyEdge(GraphNode<?> from, GraphNode<?> to){
        addDummyEdge(from, to, new DummyEdge());
    }

    @Override
    public void build(CallableDeclaration<?> callableDeclaration) {
        built = true;
        callableDeclaration.accept(new ACFGBuilder(this),null);
        addDummyEdge(this.root,this.exit,new DummyEdge());
    }
}
