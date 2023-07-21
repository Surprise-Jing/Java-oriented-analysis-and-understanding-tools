package com.nju.boot.graphs.augmented;

import com.github.javaparser.ast.stmt.*;
import com.nju.boot.graphs.cfg.CFGBuilder;
import com.nju.boot.nodes.GraphNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * ACFG的Builder
 * */
public class ACFGBuilder extends CFGBuilder {
    /**
     * 待连接的伪判断节点
     */
    List<GraphNode<?>> jumpVertexes = new ArrayList<>();

    /**
     * 连向return语句如果没有执行，
     * 下一个将会执行的节点
     * @param returnStmt
     * @param arg
     */
    @Override
    public void visit(ReturnStmt returnStmt, Void arg) {
        GraphNode<ReturnStmt> node = connectTo(returnStmt);
        returnList.add(node);
        clearProcessing();
        jumpVertexes.add(node);
    }

    /**
     * 连向swith语句如果没有执行
     * 下一个会执行的语句
     * @param switchStmt
     * @param arg
     */

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
        jumpVertexes.add(condNode);
        processingNodes.addAll(breakStack.pop());
    }

    /**
     * 连接待连接节点
     * 对于伪判断节点，使用伪控制流边进行连接
     * @param node
     */
    @Override
    protected void connectTo(GraphNode<?> node) {
        ACFG acfg = (ACFG) this.cfg;
        for(GraphNode<?>vertex:jumpVertexes){
            acfg.addDummyEdge(vertex,node);
        }
        jumpVertexes.clear();
        super.connectTo(node);
    }

    @Override
    protected void clearProcessing() {

        super.clearProcessing();
    }

    /**
     * 指向continue语句如果没有执行
     * 下一句会执行的语句
     * @param continueStmt
     * @param arg
     */
    @Override
    public void visit(ContinueStmt continueStmt, Void arg) {
        GraphNode<ContinueStmt> node = connectTo(continueStmt);
        if(!continueStmt.getLabel().isPresent()){
            continueStack.peek().add(node);
        }
        clearProcessing();
        jumpVertexes.add(node);

    }

    /**
     * 指向break语句如果没有执行
     * 下一句会执行的语句
     * @param breakStmt
     * @param arg
     */

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
