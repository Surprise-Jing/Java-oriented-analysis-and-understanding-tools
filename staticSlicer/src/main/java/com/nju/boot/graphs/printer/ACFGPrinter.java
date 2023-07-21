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

/**
 * ACFG的打印类
 */
public class ACFGPrinter extends CFGPrinter{
    public ACFGPrinter(ACFG cfg, Writer writer) {
        super(cfg, writer);
    }
    public ACFGPrinter(ACFG cfg, Writer writer,Format format) {
        super(cfg, writer,format);
    }


    /**
     * 为exporter添加节点、边的属性提供者
     * @param exporter
     */
    @Override
    protected void setUpExporter(BaseExporter<GraphNode<?>, Edge> exporter) {
        exporter.setVertexAttributeProvider(v->{
            Map<String, Attribute> map = new HashMap<>();
                map.put("label",DefaultAttribute.createAttribute(v.getInstruction()));
            return map;
        });
        /**
         * 如果是伪边
         */
        exporter.setEdgeAttributeProvider(e->{
            Map<String, Attribute> map = new HashMap<>();
            if(e instanceof DummyEdge)
            /**
             * 以虚线表示
             */
                map.put("style",DefaultAttribute.createAttribute("dashed"));
            return map;
        });

    }
}
