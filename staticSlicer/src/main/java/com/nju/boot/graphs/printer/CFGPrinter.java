package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CFGPrinter extends  GraphPrinter{
    CFG cfg;
    DOTExporter<GraphNode<?>, Edge> dotExporter = new DOTExporter<>(node -> String.valueOf(node.getId()));
    public CFGPrinter(CFG cfg, Writer writer) {
        this.cfg = cfg;
        setWriter(writer);
    }
    private void setUpDotExporter(){
        dotExporter.setVertexAttributeProvider(v->{
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
            return map;
        });
    }

    @Override
    public void print() {
        dotExporter.exportGraph(cfg,writer);
    }
}
