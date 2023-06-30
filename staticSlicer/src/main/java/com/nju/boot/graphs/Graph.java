package com.nju.boot.graphs;

import com.nju.boot.edges.Edge;
import com.nju.boot.nodes.GraphNode;
import com.github.javaparser.ast.Node;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.dot.DOTExporter;

import java.util.Optional;

public abstract class Graph<RootN extends Node> extends DefaultDirectedGraph<GraphNode<?>, Edge> {

    protected GraphNode<RootN> root;
    protected int nextId;

    protected Graph(int StartId){
        super(null, null, false);
        this.nextId = StartId;
    }
    public Graph(){
        this(1);
    }

    private <N extends Node> GraphNode<N> addNode(GraphNode<N> node){
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

    public Optional<GraphNode<?>> getRootNode(){
        return Optional.ofNullable(root);
    }


}
