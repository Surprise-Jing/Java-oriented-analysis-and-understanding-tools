package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.Graph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.BaseExporter;
import org.jgrapht.nio.DefaultAttribute;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CFGPrinter extends  GraphPrinter{

    public CFGPrinter(CFG cfg, Writer writer) {
        super(cfg,writer);
    }

    public CFGPrinter(CFG cfg, Writer writer, Format format) {
        super(cfg, writer, format);
    }

    @Override
    protected void setUpExporter(BaseExporter<GraphNode<?>, Edge> exporter) {
        exporter.setVertexAttributeProvider(v->{
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
            if(graph.outgoingEdgesOf(v).size()>1)
                map.put("shape",DefaultAttribute.createAttribute("diamond"));
            return map;
        });
    }
}
