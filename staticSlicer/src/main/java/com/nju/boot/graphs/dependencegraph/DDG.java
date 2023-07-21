package com.nju.boot.graphs.dependencegraph;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.printer.PDGPrinter;

import java.io.Writer;

/***
 * 数据依赖图，边都是数据依赖关系
 */
public class DDG extends DependenceGraph{
    public void buildFromACFG(ACFG acfg){
        if(built)return;
        built = true;
        this.acfg = acfg;
        acfg.vertexSet().stream().filter(vertex->vertex!=acfg.getExitNode().get()).forEach(this::addVertex);
        buildDataDependency(this);
    }
    public void build(CallableDeclaration<?> callableDeclaration){
        if(built)return;
        acfg = new ACFG();
        acfg.build(callableDeclaration);
        buildFromACFG(acfg);
    }

    @Override
    protected void writeAsDot(Writer writer) {
        return;
    }
}
