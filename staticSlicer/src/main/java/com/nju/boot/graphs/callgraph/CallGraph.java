package com.nju.boot.graphs.callgraph;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;

import com.nju.boot.graphs.printer.CallGraphPrinter;
import com.nju.boot.graphs.printer.GraphPrinter;
import com.nju.boot.nodes.GraphNode;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * 函数调用图
 * 向build传入CompilationUnit来进行构建
 * */
public class CallGraph extends Graph<CallableDeclaration<?>> {
    Map<String, GraphNode<CallableDeclaration<?>>> signatureToNodeMap;
    boolean built = false;

    /**
     * 是否在节点中包括外部引入的方法
     * */
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
    /**
     * 以json格式输出
     * */
    @Override
    public String toString() {
        StringWriter stringWriter =  new StringWriter();
        CallGraphPrinter callGraphPrinter = new CallGraphPrinter(this,stringWriter);
        callGraphPrinter.print();
        return stringWriter.toString();
    }

    @Override
    protected void writeAsDot(Writer writer) {
        new CallGraphPrinter(this,writer, GraphPrinter.Format.DOT).print();
    }
}
