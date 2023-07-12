package com.nju.boot.slicer;

import com.nju.boot.nodes.GraphNode;

import java.util.Set;

public abstract class AbstractSlicer {
    public abstract Set<GraphNode<?>> slice(int lineNumber,String variable);
}
