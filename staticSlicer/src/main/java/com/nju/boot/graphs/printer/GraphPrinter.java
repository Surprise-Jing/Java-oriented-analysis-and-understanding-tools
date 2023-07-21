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

/**
 * 图的抽象打印类
 */
public abstract class GraphPrinter{
    /**
     * 需要打印的图
     */
    Graph graph;
    /**
     * 导出时的writer对象
     */
    Writer writer;
    /**
     * json格式的导出者
     */
    JSONExporter<GraphNode<?>,Edge> jsonExporter = new JSONExporter<>(v->String.valueOf(v.getId()));
    /**
     * dot格式的导出者
     */
    DOTExporter<GraphNode<?>,Edge> dotExporter = new DOTExporter<>(v->String.valueOf(v.getId()));

    /**
     * 枚举类型，枚举了可以打印的格式
     */
    public enum Format{
        JSON,DOT
    }

    /**
     * 设置打印的格式
     * @param format 打印的格式，有json和dot两种
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    Format format;

    /**
     *
     * @param graph 需要打印的图
     * @param writer 打印到的writer
     * @param format 打印的格式，有Json和Dot两种
     */
    public GraphPrinter(Graph graph, Writer writer,Format format) {
        this.graph = graph;
        this.writer = writer;
        this.format = format;
        setUpExporter();
    }

    /**
     * 默认的打印格式为json
     * @param graph 要打印的图
     * @param writer 打印到的对象
     */
    public GraphPrinter(Graph graph, Writer writer) {
        this(graph,writer,Format.JSON);
    }

    public Writer getWriter() {
        return writer;
    }

    /**
     * 设置dot导出者和json导出者的边、点属性
     */
    protected void setUpExporter(){
        setUpExporter(jsonExporter);
        setUpExporter(dotExporter);
    }
    protected abstract void setUpExporter(BaseExporter<GraphNode<?>,Edge> exporter);
    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * 调用该函数以指定格式打印到writer
     */
    public void print(){
        if(format == Format.JSON)
            jsonExporter.exportGraph(graph,writer);
        else if (format == Format.DOT)
            dotExporter.exportGraph(graph,writer);
    }

}
