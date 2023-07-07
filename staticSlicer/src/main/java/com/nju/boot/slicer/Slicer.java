package com.nju.boot.slicer;

import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.nju.boot.graphs.pdg.PDG;
import com.nju.boot.graphs.printer.SelectivePrettyPrinter;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.util.SlicerUtil;

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
        return SlicerUtil.findMethodByLineNumber(graphs.getCu(),lineNumber);
    }
    public String getResult(){
        return result;
    }
}
