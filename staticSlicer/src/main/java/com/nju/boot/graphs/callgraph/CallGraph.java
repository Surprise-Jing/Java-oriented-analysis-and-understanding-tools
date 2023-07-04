package com.nju.boot.graphs.callgraph;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.nodes.GraphNode;

import java.util.Map;

public class CallGraph extends Graph<CallableDeclaration<?>> {
    Map<String, GraphNode<?>> signatureToNodeMap;
    boolean built = false;

    public void setIncludeImportedFunctions(boolean includeImportedFunctions) {
        this.includeImportedFunctions = includeImportedFunctions;
    }

    boolean includeImportedFunctions = true;

    public Map<String, GraphNode<?>> getSignatureToNodeMap() {
        return signatureToNodeMap;
    }

    public void setSignatureToNodeMap(Map<String, GraphNode<?>> signatureToNodeMap) {
        this.signatureToNodeMap = signatureToNodeMap;
    }

    public void build(CompilationUnit cu){
        if(built)return;
        new VertexBuilder(this).visit(cu,null);
        new EdgeBuilder(this,includeImportedFunctions).visit(cu,null);
        built = true;
    }
}
