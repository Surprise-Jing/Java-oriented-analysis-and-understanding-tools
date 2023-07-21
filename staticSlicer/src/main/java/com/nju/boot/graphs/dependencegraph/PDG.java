package com.nju.boot.graphs.dependencegraph;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.ControlDependencyEdge;
import com.nju.boot.edges.DataDependencyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.printer.GraphPrinter;
import com.nju.boot.graphs.printer.PDGPrinter;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.util.PDGMarker;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

public class PDG extends DependenceGraph {

    /**
     *
     * @return 图中被标记的节点集合
     */
    public Set<GraphNode<?>> getMarkedNodes() {
        return markedNodes;
    }

    /**
     * 设置markedNodes，为该图节点集合的子集
     * @param markedNodes
     */
    public void setMarkedNodes(Set<GraphNode<?>> markedNodes) {
        this.markedNodes = markedNodes;
    }

    /**
     * 从源控制流图中生成的后支配树
     */
    DominatorTree postDominatorTree;

    public DominatorTree getPostDominatorTree() {
        return postDominatorTree;
    }

    /**
     * 被标记的节点
     */
    Set<GraphNode<?>> markedNodes = new HashSet<>();

    /**
     * 从PDG图进行切片，对其中节点进行标记
     * @param lineNumber 切片准则中的行号
     * @param variable 切片准则中的变量名
     */
    public void slice(int lineNumber,String variable){
        PDGMarker pdgMarker = new PDGMarker(this);
        pdgMarker.mark(lineNumber,variable);
        markedNodes = pdgMarker.getMarkedVertices();
    }
    public boolean isMarked(GraphNode<?>node){
        return markedNodes.contains(node);
    }

    /**
     * 建立PDG的方法
     * @param callableDeclaration 指定从其建立PDG的方法
     */
    public void build(CallableDeclaration<?> callableDeclaration){
        if(built)return;
        acfg = new ACFG();
        acfg.build(callableDeclaration);
        buildFromACFG(acfg);
    }

    /**
     * 导出json格式的图
     * @return
     */
    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        new PDGPrinter(this,stringWriter).print();
        return stringWriter.toString();
    }


    /**
     * 以acfg作为源构建pdg图
     * @param acfg
     */
    public void buildFromACFG(ACFG acfg){
        if(built) return;
        built = true;
        this.acfg = acfg;
        //把acfg的节点集合都添加到pdg
        //除了exit外，应该具有相同的结点
        acfg.vertexSet().stream().filter(vertex->vertex!=acfg.getExitNode().get()).forEach(this::addVertex);
        //构建数据依赖图
        DDG ddg = new DDG();
        ddg.buildFromACFG(acfg);
        //从数据依赖图中获取数据依赖边
        ddg.edgeSet().forEach(e->{
            GraphNode<?>src = ddg.getEdgeSource(e),
                    tar = ddg.getEdgeTarget(e);
            assert  e instanceof DataDependencyEdge;
            this.addDataDependencyEdge(src,tar,(DataDependencyEdge) e);
        });
        //构建控制依赖图
        CDG cdg = new CDG();
        cdg.buildFromACFG(acfg);
        //从控制依赖图中获取控制依赖边
        cdg.edgeSet().forEach(e->{
            GraphNode<?>src = ddg.getEdgeSource(e),
                    tar = ddg.getEdgeTarget(e);
            assert e instanceof ControlDependencyEdge;
            this.addControlDependencyEdge(src,tar,(ControlDependencyEdge) e);

        });


        //传入转置的acfg来获取后支配树
        postDominatorTree = new DominatorTree(acfg.reverse());
        postDominatorTree.build();
//
    }


    @Override
    protected void writeAsDot(Writer writer) {
        new PDGPrinter(this,writer, GraphPrinter.Format.DOT).print();
    }
}
