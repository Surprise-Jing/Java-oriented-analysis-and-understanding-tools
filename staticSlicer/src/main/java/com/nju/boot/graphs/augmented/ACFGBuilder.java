package com.nju.boot.graphs.augmented;

import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.nju.boot.graphs.cfg.CFGBuilder;
import com.nju.boot.nodes.GraphNode;

import java.util.ArrayList;
import java.util.List;

public class ACFGBuilder extends CFGBuilder {
    List<GraphNode<?>> jumpVertexes = new ArrayList<>();

    @Override
    protected void connectTo(GraphNode<?> node) {
        ACFG acfg = (ACFG) this.cfg;
        for(GraphNode<?>vertex:jumpVertexes){
            acfg.addDummyEdge(vertex,node);
        }
        super.connectTo(node);
    }

    @Override
    protected void clearProcessing() {
        jumpVertexes.clear();
        super.clearProcessing();
    }

    @Override
    public void visit(ContinueStmt continueStmt, Void arg) {
        GraphNode<ContinueStmt> node = connectTo(continueStmt);
        if(!continueStmt.getLabel().isPresent()){
            continueStack.peek().add(node);
        }
        clearProcessing();
        jumpVertexes.add(node);

    }

    @Override
    public void visit(BreakStmt breakStmt, Void arg) {
        GraphNode<BreakStmt> node = connectTo(breakStmt);
        if(!breakStmt.getLabel().isPresent()){
            breakStack.peek().add(node);
        }
        clearProcessing();
        jumpVertexes.add(node);
    }

    ACFGBuilder(ACFG acfg){
        super(acfg);
    }

}
