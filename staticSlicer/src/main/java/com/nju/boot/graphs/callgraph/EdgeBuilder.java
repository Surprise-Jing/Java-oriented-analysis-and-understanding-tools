package com.nju.boot.graphs.callgraph;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.nju.boot.edges.CallEdge;
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

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        String sig = n.resolve().getQualifiedSignature();
        GraphNode<?> target = callGraph.getSignatureToNodeMap().get(sig);
        if(target!=null){
            callGraph.addEdge(currentMethodNode,target,new CallEdge());
        }
        else if (includeImportedFunctions){
            target = callGraph.addNode(sig,null);
            callGraph.getSignatureToNodeMap().put(sig,target);
            callGraph.addEdge(currentMethodNode,target,new CallEdge());
        }
        super.visit(n, arg);
    }
}
