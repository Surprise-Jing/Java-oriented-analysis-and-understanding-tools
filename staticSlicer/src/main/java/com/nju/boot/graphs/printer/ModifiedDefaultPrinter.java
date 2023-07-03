package com.nju.boot.graphs.printer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.Printer;
import com.github.javaparser.printer.configuration.PrinterConfiguration;
import com.nju.boot.nodes.GraphNode;

import java.util.HashSet;
import java.util.Set;

public class ModifiedDefaultPrinter implements Printer {
    DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter();
    Set<GraphNode<?>> graphNodes;

    public ModifiedDefaultPrinter(Set<GraphNode<?>> graphNodes) {
        this.graphNodes = graphNodes;
    }

    @Override
    public String print(Node node) {

        return null;
    }

    @Override
    public Printer setConfiguration(PrinterConfiguration configuration) {
        defaultPrettyPrinter.setConfiguration(configuration);
        return this;
    }

    @Override
    public PrinterConfiguration getConfiguration() {
        return defaultPrettyPrinter.getConfiguration();
    }
}
