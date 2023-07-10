package com.nju.boot.graphs.dependencegraph;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.printer.PDGPrinter;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.util.PDGMarker;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

public class PDG extends DependenceGraph {

    public Set<GraphNode<?>> getMarkedNodes() {
        return markedNodes;
    }

    public void setMarkedNodes(Set<GraphNode<?>> markedNodes) {
        this.markedNodes = markedNodes;
    }



    Set<GraphNode<?>> markedNodes = new HashSet<>();
    public void slice(int lineNumber,String variable){
        PDGMarker pdgMarker = new PDGMarker(this);
        pdgMarker.mark(lineNumber,variable);
        markedNodes = pdgMarker.getMarkedVertices();
    }
    public boolean isMarked(GraphNode<?>node){
        return markedNodes.contains(node);
    }


    public void build(CallableDeclaration<?> callableDeclaration){
        if(built)return;
        acfg = new ACFG();
        acfg.build(callableDeclaration);
        buildFromACFG(acfg);
    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        new PDGPrinter(this,stringWriter).print();
        return stringWriter.toString();
    }



    public void buildFromACFG(ACFG acfg){
        if(built) return;
        built = true;
        this.acfg = acfg;
        acfg.vertexSet().stream().filter(vertex->vertex!=acfg.getExitNode().get()).forEach(this::addVertex);
        buildControlDependency(this);
        buildDataDependency(this);

    }


}
