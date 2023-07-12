package com.nju.boot.slicer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.printer.SelectivePrettyPrinter;
import com.nju.boot.util.PDGMarker;
import com.nju.boot.util.GraphsUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PDGSlicer extends AbstractSlicer{

    Graphs graphs;
    Set<GraphNode<?>>slicedNodes;

    public PDGSlicer(String fileName) {
        graphs = new Graphs(fileName);
    }
    public PDGSlicer(Graphs graphs){
        this.graphs = graphs;
    }

    public AbstractSlicer slice(int lineNumber, String variableName){
        CallableDeclaration<?> targetDeclaration = findCallableDeclaration(lineNumber);
        PDG pdg = graphs.getPDG(targetDeclaration);
        pdg.slice(lineNumber,variableName);
        slicedNodes = pdg.getMarkedNodes();
        return this;
    }

    @Override
    public Set<GraphNode<?>> getSlicedGraphNode() {
        return slicedNodes;
    }




    /**根据行号找到方法声明*/
    public CallableDeclaration<?> findCallableDeclaration(int lineNumber){
        return GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber);
    }

    public String getSlicedCode(int lineNumber,String variableName){
        CallableDeclaration<?> callableDeclaration =  GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber);
        PDG targetPDG = graphs.getPDG(callableDeclaration);
        PDGMarker pdgMarker = new PDGMarker(targetPDG);
        pdgMarker.mark(lineNumber,variableName);
        SelectivePrettyPrinter selectivePrettyPrinter =  new SelectivePrettyPrinter(targetPDG.getMarkedNodes().stream()
                .map(node -> node.getAstNode()).collect(Collectors.toSet()));
        return selectivePrettyPrinter.print(graphs.getCu());
    }
}
