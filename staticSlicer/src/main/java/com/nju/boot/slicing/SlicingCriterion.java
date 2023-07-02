package com.nju.boot.slicing;

import com.github.javaparser.ast.Node;
import com.nju.boot.graphs.Graph;
import com.nju.boot.nodes.GraphNode;

import java.util.Set;
import java.util.stream.Collectors;

public class SlicingCriterion {

    protected String variable;

    protected int lineNumber;

    protected Set<GraphNode<?>> node;

    SlicingCriterion(String variable, int lineNumber, Graph graph){
        this.variable = variable;
        this.lineNumber = lineNumber;
        this.node = findNodeByLineNumber(graph);
    }

    private Set<GraphNode<?>> findNodeByLineNumber(Graph<?> graph){
        return graph.vertexSet().stream().filter(n->{
            Node astNode = n.getAstNode();
            if(!astNode.getBegin().isPresent() || !astNode.getEnd().isPresent())
                return false;
            int begin = astNode.getBegin().get().line;
            int end = astNode.getEnd().get().line;
            return lineNumber == begin || lineNumber == end;
        }).collect(Collectors.toSet());
    }


}
