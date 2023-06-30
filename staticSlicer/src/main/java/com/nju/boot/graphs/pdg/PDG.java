package com.nju.boot.graphs.pdg;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.ControlFlowEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.graph.EdgeReversedGraph;

public class PDG extends Graph<MethodDeclaration> {
    CFG cfg = null;
    CFG reversedCfg = null;

    public void buildReversedCfg(){
        if(cfg ==null)return;
        if(reversedCfg ==null){
            reversedCfg = new CFG();
            cfg.vertexSet().stream().forEach(graphNode -> reversedCfg.addVertex(graphNode));
            for(Edge edge : cfg.edgeSet()){
               GraphNode<?>srcVertex = cfg.getEdgeSource(edge);
               GraphNode<?> destVertex = cfg.getEdgeTarget(edge);
               reversedCfg.addEdge(destVertex,srcVertex);
            }
        }
    }
    public void buildPostDominatorTree(){

    }
    public void buildDominatorTree(){

    }
    private boolean built = false;
    public void build(MethodDeclaration methodDeclaration){
        if(built)return;
        built = true;
        cfg = new CFG();
        cfg.build(methodDeclaration);
        cfg.vertexSet().stream().forEach(this::addNode);
        buildControlDependency();
        buildDataDependency();

    }
    public void buildFromCFG(CFG cfg){
        this.cfg = cfg;
        cfg.vertexSet().stream().forEach(this::addNode);
        buildControlDependency();
        buildDataDependency();
        built = true;
    }
    public void buildControlDependency(){


    }
    public void buildDataDependency(){

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
    public void addDataDependencyEdge(GraphNode<?> from, GraphNode<?> to){
        addDataDependencyEdge(from, to, new DataDependencyEdge());
    }

}
