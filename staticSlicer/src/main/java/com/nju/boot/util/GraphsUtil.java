package com.nju.boot.util;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.printer.DotPrinter;
import com.github.javaparser.printer.XmlPrinter;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.exceptions.MethodNotFoundException;
import com.nju.boot.slicer.exceptions.VariableNotFoundException;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphsUtil {
    /**
     *
     * @param cu ast树根节点
     * @param lineNumber 行号
     * @return 该行所在的方法
     */
    public static CallableDeclaration<?>  findMethodByLineNumber(CompilationUnit cu,int lineNumber){

        for (TypeDeclaration<?> typeDeclaration:cu.getTypes()){
            for (BodyDeclaration<?> bodyDeclaration:typeDeclaration.getMembers()){
                if(bodyDeclaration instanceof CallableDeclaration<?>){
                    Range rangeOfBody = bodyDeclaration.getRange().get();
                    if(rangeOfBody.begin.line<=lineNumber&&rangeOfBody.end.line>=lineNumber)
                        return(CallableDeclaration<?>) bodyDeclaration;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param graphs 所有抽象模型信息
     * @param signature 函数签名
     * @return 找到的函数
     */
    public static CallableDeclaration<?> findMethodBySignature(Graphs graphs, String signature){

        return graphs.getCallGraph().getSignatureToNodeMap().get(signature).getAstNode();
    }

    /**
     * 将ast树打印成xml格式
     * @param compilationUnit ast树的根节点
     * @return xml格式的字符串
     */
    public static String astNodeToXml(CompilationUnit compilationUnit){
        XmlPrinter xmlPrinter = new XmlPrinter(true);
        return xmlPrinter.output(compilationUnit);
    }

    /**
     * 将ast树打印成png图的格式
     * @param compilationUnit ast树的根节点
     * @param outFile 保存到的文件
     * @throws IOException
     */
    public static void astNodeToPNGOutput(CompilationUnit compilationUnit, File outFile) throws IOException {
        String srcDot = new DotPrinter(true).output(compilationUnit);
        Graphviz.fromString(srcDot).render(Format.PNG).toOutputStream(new FileOutputStream(outFile));
    }

    /**
     * 将ast树保存为png图的格式
     * @param compilationUnit ast树根节点
     * @param outFilePath 保存到的文件路径
     * @throws IOException
     */
    public static void astNodeToPNGOutput(CompilationUnit compilationUnit, String outFilePath) throws IOException {
        File outFile = new File(outFilePath);
        astNodeToPNGOutput(compilationUnit,outFile);
    }

    /**
     * 将ast树保存为png图的格式
     * @param compilationUnit ast树根节点
     * @param outputStream 保存到的输出流
     * @throws IOException
     */
    public static void astNodeToPNGOutput(CompilationUnit compilationUnit, OutputStream outputStream) throws IOException {
        String srcDot = new DotPrinter(true).output(compilationUnit);
        Graphviz.fromString(srcDot).render(Format.PNG).toOutputStream(outputStream);
    }

    /**
     *
     * @param node
     * @return 该节点包含的所有行序号集合
     */
    public static Set<Integer> getLinesCoveredByNode(Node node){
        int start = node.getRange().get().begin.line;
        int end = node.getRange().get().end.line;
        return IntStream.range(start,end+1).boxed().collect(Collectors.toSet());
    }

    /**
     *
     * @param nodes
     * @return 所有节点包含的所有行序号集合
     */
    public static Set<Integer> getLinesCoveredByNodes(Set<Node>nodes){
        Set<Integer>result = new HashSet<>();
        nodes.stream().map(GraphsUtil::getLinesCoveredByNode).forEach(result::addAll);
        return result;
    }

    /**
     *
     * @param g 包含所有节点的图
     * @param lineNumber 切片准则中的行号
     * @param variable 切片准则中的变量名
     * @return 对应的节点
     */
    public static GraphNode<?> getNodeBy(Graph<?> g,int lineNumber,String variable){
        GraphNode<?>result = null;
        for(GraphNode<?>graphNode:g.vertexSet()){

            int lineBegin = graphNode.getAstNode().getBegin().get().line,
                    lineEnd = graphNode.getAstNode().getEnd().get().line;
            if(lineBegin<=lineNumber&&lineEnd>=lineNumber){
                if (graphNode.getUsedVariables().contains(variable)
                        ||graphNode.getDefinedVariables().contains(variable)) {
                    result = graphNode;
                    break;
                }
            }
        }
        if(result == null){
            throw new VariableNotFoundException();
        }
        return result;
    }

    /**
     * @param graphs 所有代码抽象模型信息
     * @param lineNumber 切片准则中的行号
     * @param variable 切片准则中的变量名
     * @return 对应的节点
     */
    public static GraphNode<?> getNodeBy(Graphs graphs,int lineNumber,String variable){
        CallableDeclaration<?>targetMethod = findMethodByLineNumber(graphs.getCu(),lineNumber);
        if(targetMethod == null)
            throw new MethodNotFoundException();
        CFG cfg = graphs.getCFG(targetMethod);
       return  getNodeBy(cfg,lineNumber,variable);

    }
}
