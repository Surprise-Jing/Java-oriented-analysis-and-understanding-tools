package com.nju.boot.graphs.cfg;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.stmt.*;
import com.nju.boot.nodes.GraphNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**给定一个CFG和一个AST根节点，填充CFG。
 * 用法:
 *  创建一个新的CFG。
 *  创建一个新的CFGBuilder，将图形作为参数传递。
 *  将builder作为MethodDeclaration的visitor接受，使用Node#accept(VoidVisitor, Object)方法: methodDec.accept(builder, null)。
 *  完成上一步后，完整的CFG将保存在第一步创建的对象中。应丢弃builder，不应重用。
 *  */
public class CFGBuilder extends VoidVisitorAdapter<Void> {

    protected CFG cfg;

    /** 当前遍历的节点，准备与下一个节点进行连接 */
    protected List<GraphNode<?>> processingNodes = new LinkedList<>();

    /** 保存BreakStmt，与跳转最终结果相连 */
    protected Deque<List<GraphNode<BreakStmt>>> breakStack = new LinkedList<>();

    /** 保存ContinueStmt，与起始条件分支相连 */
    protected Deque<List<GraphNode<ContinueStmt>>> continueStack = new LinkedList<>();

    /** 保存SwitchEntryStmt，即case语句，每个case语句之间要相连 */
    protected Deque<List<GraphNode<SwitchEntry>>> switchEntriesStack = new LinkedList<>();

    /** 保存ReturnStmt，最终与EXIT节点相连*/
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
        // *if* -> {then else} -> after
        GraphNode<?> condNode = connectTo(ifStmt, ifStmt.getCondition().toString());

        // if -> {*then* else} -> after
        ifStmt.getThenStmt().accept(this, arg);
        List<GraphNode<?>> thenNodes = new LinkedList<>(processingNodes);

        if(ifStmt.getElseStmt().isPresent()){
            // if -> {then *else*} -> after
            clearProcessing();
            processingNodes.add(condNode);
            ifStmt.getElseStmt().get().accept(this, arg);
            processingNodes.addAll(thenNodes);
        }
        else {
            // if -> {then **} -> after
            processingNodes.add(condNode);
        }
        // if -> {then else} -> *after*
    }

    @Override
    public void visit(SwitchEntry switchEntry, Void arg){
        // Case header (prev -> case EXPR)
        GraphNode<SwitchEntry> entryNode;
        if(switchEntry.getLabels().isEmpty()){
            entryNode = connectTo(switchEntry, "default");
        }
        else{
            entryNode = connectTo(switchEntry, "case " + switchEntry.getLabels().get(0));
        }
        switchEntriesStack.peek().add(entryNode);
        // Case body (case EXPR --> body)
        switchEntry.getStatements().accept(this, arg);
        // body --> next
    }

    @Override
    public void visit(SwitchStmt switchStmt, Void arg){
        switchEntriesStack.push(new LinkedList<>());
        breakStack.push(new LinkedList<>());
        GraphNode<?> condNode = connectTo(switchStmt, String.format("switch (%s)", switchStmt.getSelector()));
        // expr --> each case
        for(SwitchEntry switchEntry : switchStmt.getEntries()){
            switchEntry.accept(this, arg);
            processingNodes.add(condNode);
        }
        // considering a default case
        processingNodes.remove(condNode);
        switchEntriesStack.pop();
        // End block and break section
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

        // Initialization
        forStmt.getInitialization().forEach(this::connectTo);

        // Condition
        Expression condition = forStmt.getCompare().orElse(new BooleanLiteralExpr(true));
        GraphNode<?> condNode = connectTo(forStmt, condition.toString());

        // Body and update expressions
        forStmt.getBody().accept(this, arg);

        // Condition if body contained anything
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

    /**接收函数方法，开始遍历AST生成CFG*/
    @Override
    public void visit(MethodDeclaration methodDeclaration, Void arg){
        cfg.buildRootNode("ENTER " + methodDeclaration.getNameAsString(), methodDeclaration);
        GraphNode<?> exitNode = cfg.buildExitNode("EXIT " + methodDeclaration.getNameAsString(), methodDeclaration);
        processingNodes.add(cfg.getRootNode().orElse(null));
        methodDeclaration.getBody().get().accept(this, arg);
        returnList.stream().filter(n-> !processingNodes.contains(n)).forEach(processingNodes::add);
        connectTo(exitNode);
    }


    /**接收构造函数，开始遍历AST生成CFG*/
    @Override
    public void visit(ConstructorDeclaration n, Void arg) {
        cfg.buildRootNode("ENTER " + n.getNameAsString(), n);
        GraphNode<?> exitNode = cfg.buildExitNode("EXIT " + n.getNameAsString(), n);
        processingNodes.add(cfg.getRootNode().orElse(null));
        n.getBody().accept(this, arg);
        returnList.stream().filter(x-> !processingNodes.contains(x)).forEach(processingNodes::add);
        connectTo(exitNode);

    }
}
