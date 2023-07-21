package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.callgraph.CallGraph;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.BaseExporter;
import org.jgrapht.nio.DefaultAttribute;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * callgraph的打印类
 */
public class CallGraphPrinter extends GraphPrinter{

    public CallGraphPrinter(CallGraph callGraph, Writer writer) {
        super(callGraph,writer);
    }
    public CallGraphPrinter(CallGraph callGraph, Writer writer,Format format) {
        super(callGraph,writer,format);
    }



    @Override
    protected void setUpExporter(BaseExporter<GraphNode<?>, Edge> exporter) {
        exporter.setVertexAttributeProvider(v->{
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
            return map;
        });
    }
}
