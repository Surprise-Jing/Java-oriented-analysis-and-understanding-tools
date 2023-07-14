package com.nju.boot.graphs.printer;

import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PDGPrinter extends GraphPrinter{

    public PDGPrinter(PDG pdg,Writer writer) {
        super(pdg,writer);
    }
    @Override
    protected void setUpExporter(){
        exporter.setVertexAttributeProvider(
                v->{
                    Map<String, Attribute> map = new HashMap<>();
                    map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
                    return map;
                }
        );
        exporter.setEdgeAttributeProvider(e->{
            Map<String,Attribute> map = new HashMap<>();
            if(e instanceof ControlDependencyEdge);
            else if (e instanceof DataDependencyEdge){
                map.put("label",DefaultAttribute
                        .createAttribute(((DataDependencyEdge) e).getDependentVariables()
                                .toString()));
                map.put("style",DefaultAttribute.createAttribute("dashed"));
            }
            return map;
        });
    }




}
