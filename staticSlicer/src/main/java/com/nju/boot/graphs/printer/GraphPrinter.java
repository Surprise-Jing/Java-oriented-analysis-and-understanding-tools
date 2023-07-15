package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.nodes.GraphNode;
import guru.nidi.graphviz.attribute.For;
import org.jgrapht.Graph;
import org.jgrapht.nio.BaseExporter;
import org.jgrapht.nio.GraphExporter;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.json.JSONExporter;

import java.io.Writer;

public abstract class GraphPrinter{
    Graph graph;
    Writer writer;
    JSONExporter<GraphNode<?>,Edge> jsonExporter = new JSONExporter<>(v->String.valueOf(v.getId()));
    DOTExporter<GraphNode<?>,Edge> dotExporter = new DOTExporter<>(v->String.valueOf(v.getId()));
    public enum Format{
        JSON,DOT
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    Format format;
    public GraphPrinter(Graph graph, Writer writer,Format format) {
        this.graph = graph;
        this.writer = writer;
        this.format = format;
        setUpExporter();
    }
    public GraphPrinter(Graph graph, Writer writer) {
        this(graph,writer,Format.JSON);
    }

    public Writer getWriter() {
        return writer;
    }
    protected void setUpExporter(){
        setUpExporter(jsonExporter);
        setUpExporter(dotExporter);
    }
    protected abstract void setUpExporter(BaseExporter<GraphNode<?>,Edge> exporter);
    public void setWriter(Writer writer) {
        this.writer = writer;
    }
    public void print(){
        if(format == Format.JSON)
            jsonExporter.exportGraph(graph,writer);
        else if (format == Format.DOT)
            dotExporter.exportGraph(graph,writer);
    }

}
