package com.nju.boot.graphs;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.printer.GraphPrinter;
import com.nju.boot.nodes.GraphNode;
import com.github.javaparser.ast.Node;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.*;
import java.util.Optional;
import java.util.WeakHashMap;

public abstract class Graph<RootN extends Node> extends DirectedPseudograph<GraphNode<?>, Edge> {

    protected GraphNode<RootN> root;
    protected GraphNode<RootN> exit;
    protected int nextId;

    protected Graph(int StartId){
        super(null, null, false);
        this.nextId = StartId;
    }
    public Graph(){
        this(1);
    }

    protected  <N extends Node> GraphNode<N> addNode(GraphNode<N> node){
        this.addVertex(node);
        return node;
    }

    private <N extends Node> GraphNode<N> addNode(int id, String instruction, N astNode){
        GraphNode<N> newNode = new GraphNode<>(id, instruction, astNode);
        return this.addNode(newNode);
    }

    public <N extends Node> GraphNode<N> addNode(String instruction, N astNode){
        return this.addNode(getNextId(), instruction, astNode);
    }

    protected int getNextId(){
        return nextId++;
    }

    public boolean buildRootNode(String instruction, RootN AstRootNode){
        if(root != null){
            return false;
        }
        GraphNode<RootN> rootNode = new GraphNode<>(0, instruction, AstRootNode);
        this.root = rootNode;
        this.addNode(rootNode);
        return true;
    }
    public GraphNode<RootN> buildExitNode(String instruction, RootN AstRootNode){
        if(exit != null){
            return null;
        }
        GraphNode<RootN> exitNode = new GraphNode<>(getNextId(), instruction, AstRootNode);
        this.exit = exitNode;
        this.addNode(exitNode);
        return exitNode;
    }

    public Optional<GraphNode<?>> getRootNode(){
        return Optional.ofNullable(root);
    }
    public Optional<GraphNode<?>> getExitNode(){
        return Optional.ofNullable(exit);
    }
    protected abstract void writeAsDot(Writer writer);
    public void save2FileAsPNG(File outFile) throws IOException {
        StringWriter stringWriter = new StringWriter();
        writeAsDot(stringWriter);
        String srcDot = stringWriter.toString();
        Graphviz.fromString(srcDot).render(Format.PNG).toFile(outFile);
    }
    public void save2FileAsPNG(String filePath) throws IOException {
        File file = new File(filePath);
        save2FileAsPNG(file);
    }
    public void save2FileAsPNG(OutputStream outputStream) throws IOException {
        StringWriter stringWriter = new StringWriter();
        writeAsDot(stringWriter);
        String srcDot = stringWriter.toString();
        Graphviz.fromString(srcDot).render(Format.PNG).toOutputStream(outputStream);
    }

}
