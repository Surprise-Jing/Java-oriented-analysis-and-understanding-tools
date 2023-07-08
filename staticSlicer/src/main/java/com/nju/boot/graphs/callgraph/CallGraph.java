package com.nju.boot.graphs.callgraph;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;

import com.nju.boot.graphs.printer.CallGraphPrinter;
import com.nju.boot.nodes.GraphNode;

import java.io.StringWriter;
import java.util.Map;

public class CallGraph extends Graph<CallableDeclaration<?>> {
    Map<String, GraphNode<CallableDeclaration<?>>> signatureToNodeMap;
    boolean built = false;

    public void setIncludeImportedFunctions(boolean includeImportedFunctions) {
        this.includeImportedFunctions = includeImportedFunctions;
    }

    boolean includeImportedFunctions = true;

    public Map<String, GraphNode<CallableDeclaration<?>>> getSignatureToNodeMap() {
        return signatureToNodeMap;
    }

    public void setSignatureToNodeMap(Map<String, GraphNode<CallableDeclaration<?>>> signatureToNodeMap) {
        this.signatureToNodeMap = signatureToNodeMap;
    }

    public void build(CompilationUnit cu){
        if(built)return;
        new VertexBuilder(this).visit(cu,null);
        new EdgeBuilder(this,includeImportedFunctions).visit(cu,null);
        built = true;
    }

    @Override
    public String toString() {
        StringWriter stringWriter =  new StringWriter();
        CallGraphPrinter callGraphPrinter = new CallGraphPrinter(this,stringWriter);
        callGraphPrinter.print();
        return callGraphPrinter.toString();
    }
}
