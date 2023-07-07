package com.nju.boot.slicer;

import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.pdg.PDG;
import com.nju.boot.nodes.GraphNode;

import java.util.HashSet;
import java.util.Set;

public class PDGMarker {
    PDG pdg;
    public PDGMarker(PDG pdg) {
        this.pdg = pdg;
        assert pdg.isBuilt();
    }
    public void mark(int lineNumber,String variable){
        //todo:replace this with nodeFindingUtil
        Set<GraphNode<?>> nodes = findNodes(lineNumber,variable);
        Set<String> variables = new HashSet<>();
        variables.add(variable);
        nodes.stream().forEach(node->backTraverse(node,variables));

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

    public Set<GraphNode<?>> findNodes(int lineNumber,String variable){
        Set<GraphNode<?>> nodes = new HashSet<>();
        for (GraphNode<?>vertex:pdg.vertexSet()){
            Node astNode = vertex.getAstNode();
            Range rangeOfNode = astNode.getRange().get();
            if(rangeOfNode.getLineCount() == lineNumber &&
                    (vertex.getUsedVariables().contains(variable))||
            vertex.getDefinedVariables().contains(variable)){
                nodes.add(vertex);
            }
        }
        return nodes;
    }

    Set<GraphNode<?>> markedVertices = new HashSet<>();
    public Set<GraphNode<?>> getMarkedVertices(){
        return markedVertices;
    }
}
