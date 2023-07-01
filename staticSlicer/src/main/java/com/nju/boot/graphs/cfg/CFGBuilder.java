package com.nju.boot.graphs.cfg;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.stmt.*;
import com.nju.boot.nodes.GraphNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CFGBuilder extends VoidVisitorAdapter<Void> {

    protected CFG cfg;

    /** Nodes just processed, ready to connected to the next node. */
    protected List<GraphNode<?>> processingNodes = new LinkedList<>();

    protected Deque<List<GraphNode<BreakStmt>>> breakStack = new LinkedList<>();

    protected Deque<List<GraphNode<ContinueStmt>>> continueStack = new LinkedList<>();

    protected Deque<List<GraphNode<SwitchEntry>>> switchEntriesStack = new LinkedList<>();

    protected List<GraphNode<ReturnStmt>> returnList = new LinkedList<>();

    protected CFGBuilder(CFG cfg){
        this.cfg = cfg;
    }


    protected void connectTo(GraphNode<?> node){
        for (GraphNode<?> src : processingNodes){
            cfg.addControlFlowEdge(src, node);
        }
        clearProcessing();
        processingNodes.add(node);
    }

    protected <N extends Node> GraphNode<N> connectTo(N n, String text){
        GraphNode<N> dest = cfg.addNode(text, n);
        connectTo(dest);
        return dest;
    }

    protected <N extends Node> GraphNode<N> connectTo(N n){
        return connectTo(n, n.toString());
    }

    protected void clearProcessing() {
        processingNodes.clear();
    }

    @Override
    public void visit(ExpressionStmt expressionStmt, Void arg){
        connectTo(expressionStmt);
    }

    @Override
    public void visit(IfStmt ifStmt, Void arg){
        GraphNode<?> condNode = connectTo(ifStmt, ifStmt.getCondition().toString());

        ifStmt.getThenStmt().accept(this, arg);
        List<GraphNode<?>> thenNodes = new LinkedList<>(processingNodes);

        if(ifStmt.getElseStmt().isPresent()){
            clearProcessing();
            processingNodes.add(condNode);
            ifStmt.getElseStmt().get().accept(this, arg);
            processingNodes.addAll(thenNodes);
        }
        else {
            processingNodes.add(condNode);
        }
    }

    @Override
    public void visit(SwitchEntry switchEntry, Void arg){
        GraphNode<SwitchEntry> entryNode;
        if(switchEntry.getLabels().isEmpty()){
            entryNode = connectTo(switchEntry, "default");
        }
        else{
            entryNode = connectTo(switchEntry, "case " + switchEntry.getLabels().get(0));
        }
        switchEntriesStack.peek().add(entryNode);
        switchEntry.getStatements().accept(this, arg);
    }

    @Override
    public void visit(SwitchStmt switchStmt, Void arg){
        switchEntriesStack.push(new LinkedList<>());
        breakStack.push(new LinkedList<>());
        GraphNode<?> condNode = connectTo(switchStmt, switchStmt.getSelector().toString());
        for(SwitchEntry switchEntry : switchStmt.getEntries()){
            switchEntry.accept(this, arg);
            processingNodes.add(condNode);
        }
        processingNodes.remove(condNode);
        switchEntriesStack.pop();
        processingNodes.addAll(breakStack.pop());
    }

    @Override
    public void visit(WhileStmt whileStmt, Void arg){
        GraphNode<?> condNode = connectTo(whileStmt, whileStmt.getCondition().toString());
        breakStack.push(new LinkedList<>());
        continueStack.push(new LinkedList<>());

        whileStmt.getBody().accept(this, arg);

        processingNodes.addAll(continueStack.pop());
        connectTo(condNode);
        processingNodes.addAll(breakStack.pop());
    }

    @Override
    public void visit(DoStmt doStmt, Void arg){
        breakStack.push(new LinkedList<>());
        continueStack.push(new LinkedList<>());

        //???
        doStmt.getBody().accept(this, arg);

        GraphNode<?> condNode = connectTo(doStmt, doStmt.getCondition().toString());
        processingNodes.addAll(breakStack.pop());
    }

    @Override
    public void visit(ForStmt forStmt, Void arg){
        breakStack.push(new LinkedList<>());
        continueStack.push(new LinkedList<>());
        forStmt.getInitialization().forEach(this::connectTo);

        Expression condition = forStmt.getCompare().orElse(new BooleanLiteralExpr(true));
        GraphNode<?> condNode = connectTo(forStmt, condition.toString());

        forStmt.getBody().accept(this, arg);

        processingNodes.addAll(continueStack.pop());
        forStmt.getUpdate().forEach(this::connectTo);

        connectTo(condNode);
        processingNodes.addAll(breakStack.pop());
    }

    @Override
    public void visit(ForEachStmt forEachStmt, Void arg){
        GraphNode<?> condNode = connectTo(forEachStmt, String.format("for (%s : %s)", forEachStmt.getVariable(), forEachStmt.getIterable()));
        breakStack.push(new LinkedList<>());
        continueStack.push(new LinkedList<>());

        forEachStmt.getBody().accept(this, arg);

        processingNodes.addAll(continueStack.pop());
        connectTo(condNode);
        processingNodes.addAll(breakStack.pop());
    }

    @Override
    public void visit(ContinueStmt continueStmt, Void arg){
        GraphNode<ContinueStmt> node = connectTo(continueStmt);
        if(!continueStmt.getLabel().isPresent()){
            continueStack.peek().add(node);
        }
        clearProcessing();
    }

    @Override
    public void visit(BreakStmt breakStmt, Void arg){
        GraphNode<BreakStmt> node = connectTo(breakStmt);
        if(!breakStmt.getLabel().isPresent()){
            breakStack.peek().add(node);
        }
        clearProcessing();
    }

    @Override
    public void visit(ReturnStmt returnStmt, Void arg){
        GraphNode<ReturnStmt> node = connectTo(returnStmt);
        returnList.add(node);
        clearProcessing();
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration, Void arg){
        cfg.buildRootNode("ENTER " + methodDeclaration.getNameAsString(), methodDeclaration);
        GraphNode<?> exitNode = cfg.buildExitNode("EXIT " + methodDeclaration.getNameAsString(), methodDeclaration);
        processingNodes.add(cfg.getRootNode().orElse(null));
        methodDeclaration.getBody().get().accept(this, arg);
        returnList.stream().filter(n-> !processingNodes.contains(n)).forEach(processingNodes::add);
        connectTo(exitNode);
    }


}
