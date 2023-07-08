package com.nju.boot.graphs.cfg;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.ControlFlowEdge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.printer.CFGPrinter;
import com.nju.boot.nodes.GraphNode;

import org.checkerframework.checker.units.qual.C;

import java.io.StringWriter;

public class CFG extends Graph<CallableDeclaration<?>> {
    protected boolean built = false;

    public CFG(){
        super();
    }

    public void addControlFlowEdge(GraphNode<?> from, GraphNode<?> to, ControlFlowEdge edge){
        super.addEdge(from, to, edge);
    }

    public void addControlFlowEdge(GraphNode<?> from, GraphNode<?> to){
        addControlFlowEdge(from, to, new ControlFlowEdge());
    }

    public void build(CallableDeclaration<?> methodDeclaration){
        methodDeclaration.accept(new CFGBuilder(this), null);
        built = true;
    }

    public boolean isBuilt() {
        return built;
    }

    public CFG reverse(){
        CFG reversedCFG = new CFG();
        reversedCFG.built =true;
        this.vertexSet().stream().forEach(reversedCFG::addVertex);
        this.edgeSet().stream().forEach(edge -> {
            GraphNode<?> src = getEdgeSource(edge),
                    target = getEdgeTarget(edge);
            reversedCFG.addEdge(target,src,new ControlFlowEdge());
        });
        reversedCFG.root = this.exit;
        reversedCFG.exit =this.root;
        return reversedCFG;
    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        new CFGPrinter(this,stringWriter).print();
        return stringWriter.toString();
    }
}
