package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CDGPrinter extends GraphPrinter{
    CDG cdg;
    DOTExporter<GraphNode<?>, Edge> dotExporter = new DOTExporter<>(v->String.valueOf(v.getId()));

    public CDGPrinter(CDG cdg, Writer writer) {
        this.cdg = cdg;
        this.writer = writer;
        setUpDotExporter();
    }
    public void setUpDotExporter(){
        dotExporter.setVertexAttributeProvider(v->{
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
            return map;
        });

    }

    @Override
    public void print() {
        dotExporter.exportGraph(cdg,writer);
    }
}
