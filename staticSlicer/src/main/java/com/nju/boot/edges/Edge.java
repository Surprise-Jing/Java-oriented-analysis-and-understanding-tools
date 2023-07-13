package com.nju.boot.edges;

import com.nju.boot.nodes.GraphNode;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;

import java.util.HashMap;
import java.util.Map;

public abstract class Edge extends DefaultEdge{

    @Override
    public String toString(){
        return String.format("%s{%d -> %d}", getClass().getName(),
                ((GraphNode<?>) getSource()).getId(), ((GraphNode<?>) getTarget()).getId());
    }

    public String getLabel(){
        return "";
    }
    public Map<String, Attribute> getDotAttributes(){
        return new HashMap<>();
    }

    public boolean isControlFlowEdge(){
        return this instanceof ControlFlowEdge;
    }
}
