package com.nju.boot.slicer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.slicer.printer.SelectivePrettyPrinter;
import com.nju.boot.util.PDGMarker;
import com.nju.boot.util.GraphsUtil;

import java.util.Set;
import java.util.stream.Collectors;

public class Slicer {
    String result;
    Graphs graphs;
    Set<Node>slicedNodes;

    public Slicer(String fileName) {
        graphs = new Graphs(fileName);
    }
    public Slicer(Graphs graphs){
        this.graphs = graphs;
    }

    public void slice(int lineNumber, String variableName){
        CallableDeclaration<?> targetDeclaration = findCallableDeclaration(lineNumber);
        PDG pdg = graphs.getPDG(targetDeclaration);
        pdg.slice(lineNumber,variableName);
        slicedNodes = pdg.getMarkedNodes().stream().map(node -> node.getAstNode()).collect(Collectors.toSet());
        SelectivePrettyPrinter selectivePrettyPrinter = new SelectivePrettyPrinter(slicedNodes);
        result = selectivePrettyPrinter.print(graphs.getCu());



    }
    /**根据行号找到方法声明*/
    public CallableDeclaration<?> findCallableDeclaration(int lineNumber){
        return GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber);
    }
    public String getResult(){
        return result;
    }
    public String getSlicedCode(int lineNumber,String variableName){
        CallableDeclaration<?> callableDeclaration =  GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber);
        PDG targetPDG = graphs.getPDG(callableDeclaration);
        PDGMarker pdgMarker = new PDGMarker(targetPDG);
        pdgMarker.mark(lineNumber,variableName);
        SelectivePrettyPrinter selectivePrettyPrinter =  new SelectivePrettyPrinter(targetPDG.getMarkedNodes().stream()
                .map(node -> node.getAstNode()).collect(Collectors.toSet()));
        return selectivePrettyPrinter.print(callableDeclaration);
    }
}
