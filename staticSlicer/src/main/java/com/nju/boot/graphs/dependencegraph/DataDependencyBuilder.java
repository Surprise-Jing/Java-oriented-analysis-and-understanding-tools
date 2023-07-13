package com.nju.boot.graphs.dependencegraph;

import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.DummyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

import java.util.HashSet;
import java.util.Set;

public class DataDependencyBuilder {
    DependenceGraph dependenceGraph;

    CFG acfg;
    public DataDependencyBuilder(DependenceGraph dependenceGraph) {
        this.dependenceGraph = dependenceGraph;
        this.acfg = this.dependenceGraph.acfg;
    }
    /**add data dependency edge to the pdg*/
    public void build(){
        //Let v and w be vertices in a CFG.
        // There is a flow dependence from vertex v to vertex w (written v →f w)
        // iff vertex v assigns to variable x, vertex w uses x,
        // and there is a path from v to w that does not include an assignment to x
        // (excluding v and w)
        // and that does not include any “dummy” edge.
        for(GraphNode<?> node:acfg.vertexSet()){
            Set<String> usedVariables = node.getUsedVariables() ;

            //do not traverse through dummy edge
            acfg.incomingEdgesOf(node).stream()
                    .filter(edge -> !(edge instanceof DummyEdge))
                    .map(edge -> acfg.getEdgeSource(edge))
                    .forEach(srcNode->backwardTraverse(node,srcNode,new HashSet<>(usedVariables),new HashSet<>()));//计算node的数据依赖
        }
    }

    public void backwardTraverse(GraphNode<?>src,GraphNode<?>target,Set<String>usedVariables,Set<GraphNode<?>>visited){

        //当遇到根节点停止遍历
        if(target == acfg.getRootNode().get())return;

        //在对象节点定义的变量集合
        Set<String> definedVariables = target.getDefinedVariables();
        //求defined variable和usedVariable的交集
        Set<String> intersectedVariables = new HashSet<>(usedVariables);
        intersectedVariables.retainAll(definedVariables);
        //如果交集不为空，两个节点间存在数据依赖关系，相关数据为交集中的所有变量
        if(!intersectedVariables.isEmpty()){
            //除去被杀死的变量
            usedVariables.removeAll(intersectedVariables);
            DataDependencyEdge edge = null;
            //构建两个节点间的数据依赖边
            if(dependenceGraph.containsEdge(target,src)){
                edge = (DataDependencyEdge) dependenceGraph.getEdge(target,src);
            }
            else {
                edge = dependenceGraph.addDataDependencyEdge(target,src);//src数据依赖于target
            }

            edge.addDependentVaraibles(intersectedVariables);
            //当在该节点使用过的变量全被杀死时停止遍历
            if (usedVariables.isEmpty())return;
        }

        //当遇到判断过的节点时停止继续
        if(visited.contains(target))return;

        visited.add(target);

        acfg.incomingEdgesOf(target)
                .stream().filter(e->!(e instanceof DummyEdge))
                .map(acfg::getEdgeSource)
                .forEach(srcOfTarget->backwardTraverse(src,srcOfTarget,new HashSet<>(usedVariables),new HashSet<>(visited)));


    }




}
