package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.BaseExporter;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CDGPrinter extends GraphPrinter{

    public CDGPrinter(CDG cdg, Writer writer) {
        super(cdg,writer);
    }
    @Override
    protected void setUpExporter(){
        exporter.setVertexAttributeProvider(v->{
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
            return map;
        });

    }

}
