package com.nju.boot.util;

import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.SlicerCriterion;

import java.util.HashSet;
import java.util.Set;

public class PDGMarker {
    PDG pdg;
    public PDGMarker(PDG pdg) {
        this.pdg = pdg;
        assert pdg.isBuilt();
    }
    public void mark(int lineNumber,String variable){
        Set<String>_variables = new HashSet<>();
        _variables.add(variable);
        SlicerCriterion criterion = new SlicerCriterion(_variables,lineNumber,pdg);
        Set<GraphNode<?>> nodes = criterion.getNodes();

        nodes.stream().forEach(node->{
            Set<String> variables = new HashSet<>();
            variables.add(variable);
            if(node.getDefinedVariables().contains(variable))
                variables.addAll(node.getUsedVariables());
            backTraverse(node,variables);
        });
        pdg.setMarkedNodes(markedVertices);

    }
    public void backTraverse(GraphNode<?>start,Set<String> variables){
        //也不考虑有数据依赖和控制依赖重边的情况
        if(markedVertices.contains(start))return;
        markedVertices.add(start);
        for(Edge e: pdg.incomingEdgesOf(start)){
            GraphNode<?>src = pdg.getEdgeSource(e);
            if(e instanceof ControlDependencyEdge)
                backTraverse(src,new HashSet<>(src.getUsedVariables()));
            else if (e instanceof DataDependencyEdge){
                Set<String>dependentVariables = ((DataDependencyEdge) e).getDependentVariables();
                Set<String>intersection = new HashSet<>(dependentVariables);
                intersection.retainAll(variables);
                if(!intersection.isEmpty()){
                    backTraverse(src,new HashSet<>(src.getUsedVariables()) );
                }

            }
        }


    }



    Set<GraphNode<?>> markedVertices = new HashSet<>();
    public Set<GraphNode<?>> getMarkedVertices(){
        return markedVertices;
    }
}
