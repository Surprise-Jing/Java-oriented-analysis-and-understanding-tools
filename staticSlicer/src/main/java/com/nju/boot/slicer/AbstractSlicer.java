package com.nju.boot.slicer;

import com.github.javaparser.ast.Node;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.util.GraphsUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractSlicer {
    /**@return cfg图的被切结点*/
    public abstract AbstractSlicer slice(int lineNumber,String variable) ;
    /**@return ast树的被切结点*/
    public abstract Set<GraphNode<?>> getSlicedGraphNode();
    public  Set<Node> getSlicedAstNode(){
        return getSlicedGraphNode().stream().map(node -> node.getAstNode()).collect(Collectors.toSet());
    }
    /**判断切片准则是否正确：行号是否有对应的ast树节点，变量名是否在该节点中使用*/
    public abstract boolean isSlicable(int lineNumber,String variable);
    public  Set<Integer> getSlicedLines(){
        return GraphsUtil.getLinesCoveredByNodes(getSlicedAstNode());
    }
    public  abstract String getResultCode();


}
