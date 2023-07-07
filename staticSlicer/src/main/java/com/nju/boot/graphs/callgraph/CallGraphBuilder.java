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

public class CallGraphBuilder extends VoidVisitorAdapter<Void> {
    Map<CallableDeclaration<?>, GraphNode<?>> callableToNodeMap = new HashMap<>();

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {

        super.visit(n, arg);
    }

    @Override
    public void visit(ConstructorDeclaration n, Void arg) {
        super.visit(n, arg);
    }
}
