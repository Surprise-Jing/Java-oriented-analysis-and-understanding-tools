package com.nju.boot.slicer;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.exceptions.MethodNotFoundException;
import com.nju.boot.slicer.printer.SelectivePrettyPrinter;
import com.nju.boot.util.GraphsUtil;

import java.util.Set;

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
        if(!isSlicable(lineNumber,variableName))
            throw new MethodNotFoundException();
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

    @Override
    public boolean isSlicable(int lineNumber, String variable) {
        try{
            GraphNode<?> g = GraphsUtil.getNodeBy(graphs,lineNumber,variable);
            return g!=null;
        }
        catch (IllegalArgumentException e){
            System.out.println("输入不合法");
            return  false;
        }


    }

    @Override
    public String getResultCode() {
        return new SelectivePrettyPrinter(getSlicedAstNode()).print(graphs.getCu());
    }


    /**根据行号找到方法声明*/
    public CallableDeclaration<?> findCallableDeclaration(int lineNumber){
        return GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber);
    }


}
