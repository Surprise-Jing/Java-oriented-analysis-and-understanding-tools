package com.nju.boot.graphs.dependencegraph;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.printer.CDGPrinter;
import com.nju.boot.graphs.printer.GraphPrinter;
import org.checkerframework.checker.units.qual.A;

import java.io.StringWriter;
import java.io.Writer;

/**
 * 控制依赖图
 * 可以从ACFG和函数名构建
 */
public class CDG extends DependenceGraph{



    public void buildFromACFG(ACFG acfg){
        if(built)return;
        built = true;
        this.acfg = acfg;
        acfg.vertexSet().stream().filter(vertex->vertex!=acfg.getExitNode().get()).forEach(this::addVertex);
        buildControlDependency(this);
    }
    public void build(CallableDeclaration<?>callableDeclaration){
        if(built)return;
        acfg = new ACFG();
        acfg.build(callableDeclaration);
        buildFromACFG(acfg);
    }

    /**
     *
     * @return json格式输出
     */
    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        new CDGPrinter(this,stringWriter).print();
        return stringWriter.toString();
    }

    @Override
    protected void writeAsDot(Writer fileWriter) {
        new CDGPrinter(this,fileWriter, GraphPrinter.Format.DOT).print();
    }
}
