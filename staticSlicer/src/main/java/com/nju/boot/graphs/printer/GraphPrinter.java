package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.Graph;
import org.jgrapht.nio.BaseExporter;
import org.jgrapht.nio.GraphExporter;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.json.JSONExporter;

import java.io.Writer;

public abstract class GraphPrinter{
    Graph graph;
    Writer writer;
    JSONExporter<GraphNode<?>,Edge> exporter = new JSONExporter<>(v->String.valueOf(v.getId()));
    public GraphPrinter(Graph graph, Writer writer) {
        this.graph = graph;
        this.writer = writer;
        setUpExporter();
    }

    public Writer getWriter() {
        return writer;
    }
    protected abstract void setUpExporter();

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
    public void print(){
        exporter.exportGraph(graph,writer);
    }

}
