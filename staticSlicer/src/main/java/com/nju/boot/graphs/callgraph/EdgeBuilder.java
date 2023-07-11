package com.nju.boot.graphs.callgraph;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.nju.boot.edges.CallEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.nodes.GraphNode;

import java.util.Stack;

public class EdgeBuilder extends VoidVisitorAdapter<Void> {
    GraphNode<?> currentMethodNode;
    boolean includeImportedFunctions;
    public EdgeBuilder(CallGraph callGraph,boolean includeImportedFunctions) {
        this.callGraph = callGraph;
        this.includeImportedFunctions = includeImportedFunctions;
    }

    CallGraph callGraph;

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        assert callGraph.getSignatureToNodeMap().containsKey(n.resolve().getQualifiedSignature());
        currentMethodNode = callGraph.getSignatureToNodeMap().get(n.resolve().getQualifiedSignature());
        super.visit(n, arg);
    }
    public void call( GraphNode<?> tar){
        //已经存在边，则增加调用次数
        if(callGraph.containsEdge(currentMethodNode,tar)) {
            Edge edge = callGraph.getEdge(currentMethodNode,tar);
            assert  edge instanceof CallEdge;
            ((CallEdge) edge).timesIncrement();
        }
        //不存在边，则新增一条调用边
        else{
            callGraph.addEdge(currentMethodNode,tar,new CallEdge());
        }
    }

    @Override
    public void visit(ExplicitConstructorInvocationStmt n, Void arg) {

        String sig = n.resolve().getQualifiedSignature();
        GraphNode<CallableDeclaration<?>> target = callGraph.getSignatureToNodeMap().get(sig);
        if(target == null){
            target = callGraph.addNode(sig,null);
            callGraph.getSignatureToNodeMap().put(sig,target);
        }
        call(target);
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        String sig = n.resolve().getQualifiedSignature();
        GraphNode<CallableDeclaration<?>> target = callGraph.getSignatureToNodeMap().get(sig);
        if(target == null && includeImportedFunctions){
            target = callGraph.addNode(sig,null);
            callGraph.getSignatureToNodeMap().put(sig,target);
        }
        call(target);
        super.visit(n, arg);
    }
}
