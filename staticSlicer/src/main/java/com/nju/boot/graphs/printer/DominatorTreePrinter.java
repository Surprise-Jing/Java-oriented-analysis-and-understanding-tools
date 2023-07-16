package com.nju.boot.graphs.printer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.DominatorTree;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.BaseExporter;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.GraphExporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 支配树的打印类
 * 仅做测试用
 */
public class DominatorTreePrinter extends GraphPrinter{

    public DominatorTreePrinter(DominatorTree dominatorTree, Writer writer) {
        super(dominatorTree,writer);
    }
    public DominatorTreePrinter(DominatorTree dominatorTree, Writer writer,Format format) {
        super(dominatorTree,writer,format);
    }



    @Override
    protected void setUpExporter(BaseExporter<GraphNode<?>, Edge> exporter) {
        exporter.setVertexAttributeProvider(v->{
            Map<String,Attribute>map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
            return map;
        });
    }

}
