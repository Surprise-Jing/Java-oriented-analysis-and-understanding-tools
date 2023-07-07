package com.nju.boot.graphs.printer;

import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.pdg.PDG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PDGPrinter extends GraphPrinter{
    PDG pdg;
    Writer writer;
    DOTExporter<GraphNode<?>, Edge> dotExporter = new DOTExporter<>(v->String.valueOf(v.getId()));
    public PDGPrinter(PDG pdg,Writer writer) {
        this.pdg = pdg;
        this.writer = writer;

    }
    public void setUpDotExporter(){
        dotExporter.setVertexAttributeProvider(
                v->{
                    Map<String, Attribute> map = new HashMap<>();
                    map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
                    if(pdg.isMarked(v))
                        map.put("color",DefaultAttribute.createAttribute("red"));
                    return map;
                }
        );
        dotExporter.setEdgeAttributeProvider(e->{
            Map<String,Attribute> map = new HashMap<>();
            if(e instanceof ControlDependencyEdge);
            else if (e instanceof DataDependencyEdge){
                map.put("label",DefaultAttribute
                        .createAttribute(((DataDependencyEdge) e).getDependentVariables()
                                .toString()));
            }
            return map;
        });
    }



    @Override
    public void print() {
        setUpDotExporter();
        dotExporter.exportGraph(pdg,writer);
    }
}
