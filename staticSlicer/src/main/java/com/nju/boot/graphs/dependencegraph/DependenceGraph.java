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
    /*
    添加控制依赖边
     */
    public void addControlDependencyEdge(GraphNode<?> from, GraphNode<?> to, ControlDependencyEdge controlDependencyEdge){
        this.addEdge(from,to,controlDependencyEdge);
    }
    /*
    添加控制依赖边
     */
    public void addControlDependencyEdge(GraphNode<?> from, GraphNode<?> to){
        addControlDependencyEdge(from, to, new ControlDependencyEdge());
    }
    /*
    添加数据依赖边
     */
    public void addDataDependencyEdge(GraphNode<?> from, GraphNode<?> to, DataDependencyEdge dataDependencyEdge){
        this.addEdge(from,to,dataDependencyEdge);
    }
    /*
    添加数据依赖边
     */
    public DataDependencyEdge addDataDependencyEdge(GraphNode<?> from, GraphNode<?> to){
        DataDependencyEdge newEdge = new DataDependencyEdge();
        addDataDependencyEdge(from, to, newEdge);
        return newEdge;
    }

    /**
     * 创建控制依赖边
     * @param dependenceGraph 需要创建控制依赖关系的的对象图
     */
    protected void buildControlDependency(DependenceGraph dependenceGraph){
        new ControlDependencyBuilder(dependenceGraph).build();
    }

    /**
     * 创建数据依赖边
     * @param dependenceGraph 需要创建数据依赖关系的对象图
     */
    protected void buildDataDependency(DependenceGraph dependenceGraph){
        new DataDependencyBuilder(dependenceGraph).build();
    }
}
