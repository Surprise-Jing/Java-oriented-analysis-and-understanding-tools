package com.nju.boot.graphs.printer;

import com.nju.boot.edges.DummyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.BaseExporter;
import org.jgrapht.nio.DefaultAttribute;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ACFGPrinter extends CFGPrinter{
    public ACFGPrinter(ACFG cfg, Writer writer) {
        super(cfg, writer);
    }
    public ACFGPrinter(ACFG cfg, Writer writer,Format format) {
        super(cfg, writer,format);
    }



    @Override
    protected void setUpExporter(BaseExporter<GraphNode<?>, Edge> exporter) {
        exporter.setVertexAttributeProvider(v->{
            Map<String, Attribute> map = new HashMap<>();
                map.put("label",DefaultAttribute.createAttribute(v.getInstruction()));
            return map;
        });
        exporter.setEdgeAttributeProvider(e->{
            Map<String, Attribute> map = new HashMap<>();
            if(e instanceof DummyEdge)
                map.put("style",DefaultAttribute.createAttribute("dashed"));
            return map;
        });

    }
}
