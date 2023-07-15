package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.GraphExporter;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CFGPrinter extends  GraphPrinter{

    public CFGPrinter(CFG cfg, Writer writer) {
        super(cfg,writer);
    }
    @Override
    protected void setUpExporter() {
        exporter.setVertexAttributeProvider(v->{
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
            if(graph.outgoingEdgesOf(v).size()>1)
                map.put("shape",DefaultAttribute.createAttribute("diamond"));
            return map;
        });
    }
}
