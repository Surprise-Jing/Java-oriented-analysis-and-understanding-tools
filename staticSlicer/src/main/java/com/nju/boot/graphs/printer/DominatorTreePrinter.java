package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.DominatorTree;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class DominatorTreePrinter extends GraphPrinter{
    DominatorTree dominatorTree;
    Writer writer;
    DOTExporter<GraphNode<?>, Edge>dotExporter= new DOTExporter<>(v->String.valueOf(v.getId()));

    public DominatorTreePrinter(DominatorTree dominatorTree, Writer writer) {
        this.dominatorTree = dominatorTree;
        this.writer = writer;
        dotExporter.setVertexAttributeProvider(v->{
            Map<String,Attribute>map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
            return map;
        });

    }


    @Override
    public void print() {
        dotExporter.exportGraph(dominatorTree,writer);
    }
}
