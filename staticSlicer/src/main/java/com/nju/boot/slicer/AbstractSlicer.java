package com.nju.boot.slicer;

import com.github.javaparser.ast.Node;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.printer.SelectivePrettyPrinter;
import com.nju.boot.util.GraphsUtil;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractSlicer {
    /**@return cfg图的被切结点*/
    public abstract AbstractSlicer slice(int lineNumber,String variable);
    /**@return ast树的被切结点*/
    public abstract Set<GraphNode<?>> getSlicedGraphNode();
    public  Set<Node> getSlicedAstNode(){
        return getSlicedGraphNode().stream().map(node -> node.getAstNode()).collect(Collectors.toSet());
    }
    public  Set<Integer> getSlicedLines(){
        return GraphsUtil.getLinesCoveredByNodes(getSlicedAstNode());
    }
    public  abstract String getResultCode();

}
