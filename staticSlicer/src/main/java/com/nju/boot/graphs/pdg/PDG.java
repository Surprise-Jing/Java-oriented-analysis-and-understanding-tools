package com.nju.boot.graphs.pdg;

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

public class PDG extends Graph<MethodDeclaration> {

    public Set<GraphNode<?>> getMarkedNodes() {
        return markedNodes;
    }

    public void setMarkedNodes(Set<GraphNode<?>> markedNodes) {
        this.markedNodes = markedNodes;
    }

    CFG cfg = null;

    public CFG getCfg() {
        return cfg;
    }

    ACFG reversedCfg = null;

    Set<GraphNode<?>> markedNodes = new HashSet<>();
    public void slice(int lineNumber,String variable){
        PDGMarker pdgMarker = new PDGMarker(this);
        pdgMarker.mark(lineNumber,variable);
        markedNodes = pdgMarker.getMarkedVertices();
    }
    public boolean isMarked(GraphNode<?>node){
        return markedNodes.contains(node);
    }

    public void buildReversedCfg(){
        if(cfg ==null)return;
        if(reversedCfg ==null){
            reversedCfg = new ACFG();
            cfg.vertexSet().stream().forEach(graphNode -> reversedCfg.addVertex(graphNode));
            for(Edge edge : cfg.edgeSet()){
               GraphNode<?>srcVertex = cfg.getEdgeSource(edge);
               GraphNode<?> destVertex = cfg.getEdgeTarget(edge);
               reversedCfg.addEdge(destVertex,srcVertex);
            }
        }
    }
    private boolean built = false;

    public boolean isBuilt() {
        return built;
    }

    public void build(MethodDeclaration methodDeclaration){
        if(built)return;
        cfg = new ACFG();
        cfg.build(methodDeclaration);
        buildFromACFG(cfg);

    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        new PDGPrinter(this,stringWriter).print();
        return stringWriter.toString();
    }

    public void buildCDG(CFG cfg){
        built = true;
        this.cfg = cfg;
        cfg.vertexSet().stream().forEach(vertex->{
            if(vertex!=cfg.getExitNode().get())
                this.addVertex(vertex);
        });
        buildControlDependency();
    }

    public void buildFromACFG(CFG cfg){
        if(built) return;
        built = true;
        this.cfg = cfg;
        assert  cfg instanceof ACFG;
        cfg.vertexSet().stream().forEach(vertex->{
            if(vertex!=cfg.getExitNode().get())
                this.addVertex(vertex);
        });
        buildControlDependency();
        buildDataDependency();

    }
    public void buildControlDependency(){
        new ControlDependencyBuilder(this).build();
    }
    public void buildDataDependency(){
        new DataDependencyBuilder(this).build();
    }
    public void addControlDependencyEdge(GraphNode<?> from, GraphNode<?> to, ControlDependencyEdge controlDependencyEdge){
        this.addEdge(from,to,controlDependencyEdge);
    }
    public void addControlDependencyEdge(GraphNode<?> from, GraphNode<?> to){
        addControlDependencyEdge(from, to, new ControlDependencyEdge());
    }
    public void addDataDependencyEdge(GraphNode<?> from, GraphNode<?> to, DataDependencyEdge dataDependencyEdge){
        this.addEdge(from,to,dataDependencyEdge);
    }
    public DataDependencyEdge addDataDependencyEdge(GraphNode<?> from, GraphNode<?> to){
        DataDependencyEdge newEdge = new DataDependencyEdge();
        addDataDependencyEdge(from, to, newEdge);
        return newEdge;
    }

}
