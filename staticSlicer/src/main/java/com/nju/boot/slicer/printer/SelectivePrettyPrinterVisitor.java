package com.nju.boot.slicer.printer;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.printer.DefaultPrettyPrinterVisitor;
import com.github.javaparser.printer.SourcePrinter;
import com.github.javaparser.printer.configuration.PrinterConfiguration;


import java.util.Set;

/**
 * 打印切片的代码结果
 * 对不在切片结果内的结点，跳过打印
 */
public class SelectivePrettyPrinterVisitor extends DefaultPrettyPrinterVisitor {
    public SelectivePrettyPrinterVisitor(PrinterConfiguration configuration,Set<Node> selectedNodes) {
        super(configuration);
        this.astNodes = selectedNodes;
    }

    public SelectivePrettyPrinterVisitor(PrinterConfiguration configuration, SourcePrinter printer, Set<Node>selectedNodes) {
        super(configuration, printer);
        this.astNodes = selectedNodes;
    }
    Set<? extends Node> astNodes;

    public void setAstNodes(Set<? extends Node> astNodes) {
        this.astNodes = astNodes;
    }

    @Override
    public void visit(ForStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(BreakStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(ReturnStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(IfStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(WhileStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(ContinueStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(ForEachStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(ExpressionStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(SwitchStmt n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        if(!astNodes.contains(n))return;
        super.visit(n, arg);
    }
}
