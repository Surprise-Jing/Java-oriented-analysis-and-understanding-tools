package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.BaseExporter;
import org.jgrapht.nio.DefaultAttribute;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * CDG的打印类
 */
public class CDGPrinter extends GraphPrinter{

    public CDGPrinter(CDG cdg, Writer writer) {
        super(cdg,writer);
    }
    public CDGPrinter(CDG cdg, Writer writer,Format format) {
        super(cdg,writer,format);
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
