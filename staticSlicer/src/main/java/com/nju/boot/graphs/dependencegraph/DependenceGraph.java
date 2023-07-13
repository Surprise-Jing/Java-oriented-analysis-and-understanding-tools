package com.nju.boot.graphs.dependencegraph;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.nodes.GraphNode;

public abstract class DependenceGraph extends Graph<MethodDeclaration> {
    protected boolean built = false;


    public boolean isBuilt() {
        return built;
    }

    protected ACFG acfg = null;
    public ACFG getAcfg(){return acfg;}
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
    protected void buildControlDependency(DependenceGraph dependenceGraph){
        new ControlDependencyBuilder(dependenceGraph).build();
    }
    protected void buildDataDependency(DependenceGraph dependenceGraph){
        new DataDependencyBuilder(dependenceGraph).build();
    }
}
