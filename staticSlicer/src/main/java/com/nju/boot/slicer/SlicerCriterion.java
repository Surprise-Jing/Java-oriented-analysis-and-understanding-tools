package com.nju.boot.slicer;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.nju.boot.graphs.Graph;
import com.nju.boot.nodes.GraphNode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SlicerCriterion {

    protected Set<String> variable;

    protected int lineNumber;

    Set<GraphNode<?>> nodes;


    public SlicerCriterion(Set<String> variable, int lineNumber, Graph<?> graph){
        if(variable == null)
            this.variable = new HashSet<>();
        else
            this.variable = new HashSet<>(variable);
        this.lineNumber = lineNumber;
        this.nodes = findNodeByLineNumber(graph);
    }

    SlicerCriterion(GraphNode<?> node, Graph<?> graph){
        this.variable = node.getUsedVariables();
        this.lineNumber = node.getAstNode().getBegin().get().line;
        this.nodes = findNodeByLineNumber(graph);
    }

    public Set<GraphNode<?>> findNodeByLineNumber(Graph<?> graph){
        return graph.vertexSet().stream().filter(n->{
            Node astNode = n.getAstNode();
            if(!astNode.getBegin().isPresent() || !astNode.getEnd().isPresent())
                return false;
            int begin = astNode.getBegin().get().line;
            int end = astNode.getEnd().get().line;
            return lineNumber == begin || lineNumber == end;
        }).collect(Collectors.toSet());
    }

    public Set<String> getVariable() {
        return variable;
    }

    public Set<GraphNode<?>> getNodes() {
        return nodes;
    }
}
