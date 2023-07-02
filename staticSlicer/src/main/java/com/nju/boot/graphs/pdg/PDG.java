package com.nju.boot.graphs.pdg;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

public class PDG extends Graph<MethodDeclaration> {
    ACFG cfg = null;
    ACFG reversedCfg = null;

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
    public void buildPostDominatorTree(){

    }
    public void buildDominatorTree(){

    }
    private boolean built = false;
    public void build(MethodDeclaration methodDeclaration){
        if(built)return;
        cfg = new ACFG();
        buildFromACFG(cfg);

    }
    public void buildFromACFG(ACFG acfg){
        if(built) return;
        built = true;
        this.cfg = acfg;
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
    public void addDataDependencyEdge(GraphNode<?> from, GraphNode<?> to){
        addDataDependencyEdge(from, to, new DataDependencyEdge());
    }

}
