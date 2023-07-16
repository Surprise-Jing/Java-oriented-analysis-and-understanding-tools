package com.nju.boot.slicer.printer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.printer.Printer;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.PrinterConfiguration;

import java.util.Set;


public class SelectivePrettyPrinter implements Printer {
    Set<Node> selectedNodes;

    public SelectivePrettyPrinter(Set<Node> selectedNodes) {
        this.selectedNodes = selectedNodes;
        configuration = new DefaultPrinterConfiguration();
    }

    PrinterConfiguration configuration;

    @Override
    public String print(Node node) {
        VoidVisitor<Void> visitor = new SelectivePrettyPrinterVisitor(configuration,selectedNodes);
        node.accept(visitor,null);
        return visitor.toString();
    }

    @Override
    public Printer setConfiguration(PrinterConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    @Override
    public PrinterConfiguration getConfiguration() {
        return this.configuration;
    }
}
