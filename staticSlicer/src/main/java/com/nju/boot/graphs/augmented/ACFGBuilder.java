package com.nju.boot.graphs.augmented;

import com.github.javaparser.ast.stmt.*;
import com.nju.boot.graphs.cfg.CFGBuilder;
import com.nju.boot.nodes.GraphNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ACFGBuilder extends CFGBuilder {
    List<GraphNode<?>> jumpVertexes = new ArrayList<>();

    @Override
    public void visit(ReturnStmt returnStmt, Void arg) {
        GraphNode<ReturnStmt> node = connectTo(returnStmt);
        returnList.add(node);
        clearProcessing();
        jumpVertexes.add(node);
    }

    @Override
    public void visit(SwitchStmt switchStmt, Void arg) {
        switchEntriesStack.push(new LinkedList<>());
        breakStack.push(new LinkedList<>());
        GraphNode<?> condNode = connectTo(switchStmt, String.format("switch (%s)", switchStmt.getSelector()));
        for(SwitchEntry switchEntry : switchStmt.getEntries()){
            switchEntry.accept(this, arg);
            processingNodes.add(condNode);
        }
        List<GraphNode<SwitchEntry>>entries =  switchEntriesStack.pop();
        GraphNode<SwitchEntry> defaultEntry =  null;


        for(GraphNode<SwitchEntry> entry:entries){
            if(entry.getInstruction().equals("default")){
                defaultEntry = entry;
                break;
            }
        }

        if(defaultEntry == null)// switch语句没有default case
        {
            entries.forEach(jumpVertexes::add);
        }
        else{
            jumpVertexes.add(defaultEntry);
            for(GraphNode<SwitchEntry> entry:entries){
                if(entry == defaultEntry)continue;
                ACFG acfg = (ACFG) this.cfg;
                acfg.addDummyEdge(entry,defaultEntry);

            }
        }
        processingNodes.remove(condNode);

        processingNodes.addAll(breakStack.pop());
    }

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
