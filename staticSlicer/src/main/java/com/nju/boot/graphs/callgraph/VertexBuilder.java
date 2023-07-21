package com.nju.boot.graphs.callgraph;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.nju.boot.nodes.GraphNode;

import java.util.HashMap;
import java.util.Map;
/**
 * 为callgraph建立结点集
 */

public class VertexBuilder extends VoidVisitorAdapter<Void> {

    Map<String, GraphNode<CallableDeclaration<?>>> signatureToNodeMap = new HashMap<>();
    CallGraph callGraph;

    public VertexBuilder(CallGraph callGraph) {
        this.callGraph = callGraph;
    }

    @Override
    public void visit(CompilationUnit n, Void arg) {
        super.visit(n, arg);
        callGraph.setSignatureToNodeMap(signatureToNodeMap);
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        GraphNode<CallableDeclaration<?>>newNode = callGraph.addNode(n.resolve().getQualifiedSignature(),n);
        signatureToNodeMap.put(n.resolve().getQualifiedSignature(),newNode);
        super.visit(n, arg);
    }

    @Override
    public void visit(ConstructorDeclaration n, Void arg) {
        GraphNode<CallableDeclaration<?>>newNode = callGraph.addNode(n.resolve().getQualifiedSignature(),n);
        signatureToNodeMap.put(n.resolve().getQualifiedSignature(),newNode);

        super.visit(n, arg);
    }
}
