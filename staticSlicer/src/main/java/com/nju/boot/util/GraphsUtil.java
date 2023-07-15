package com.nju.boot.util;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.printer.DotPrinter;
import com.github.javaparser.printer.XmlPrinter;
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


    public static CallableDeclaration<?> findMethodBySignature(Graphs graphs, String signature){

        return graphs.getCallGraph().getSignatureToNodeMap().get(signature).getAstNode();
    }
    public static String astNodeToXml(CompilationUnit compilationUnit){
        XmlPrinter xmlPrinter = new XmlPrinter(true);
        return xmlPrinter.output(compilationUnit);
    }
    public static void astNodeToPNGOutput(CompilationUnit compilationUnit, File outFile) throws IOException {
        String srcDot = new DotPrinter(true).output(compilationUnit);
        Graphviz.fromString(srcDot).render(Format.PNG).toOutputStream(new FileOutputStream(outFile));
    }
    public static void astNodeToPNGOutput(CompilationUnit compilationUnit, String outFilePath) throws IOException {
        File outFile = new File(outFilePath);
        astNodeToPNGOutput(compilationUnit,outFile);
    }

    public static Set<Integer> getLinesCoveredByNode(Node node){
        int start = node.getRange().get().begin.line;
        int end = node.getRange().get().end.line;
        return IntStream.range(start,end+1).boxed().collect(Collectors.toSet());
    }
    public static Set<Integer> getLinesCoveredByNodes(Set<Node>nodes){
        Set<Integer>result = new HashSet<>();
        nodes.stream().map(GraphsUtil::getLinesCoveredByNode).forEach(result::addAll);
        return result;
    }
    public static GraphNode<?> getNodeBy(Graphs graphs,int lineNumber,String variable){
        CallableDeclaration<?>targetMethod = findMethodByLineNumber(graphs.getCu(),lineNumber);
        if(targetMethod == null)
            throw new MethodNotFoundException();
        CFG cfg = graphs.getCFG(targetMethod);
        GraphNode<?>result = null;
        for(GraphNode<?>graphNode:cfg.vertexSet()){

            int lineBegin = graphNode.getAstNode().getBegin().get().line,
                    lineEnd = graphNode.getAstNode().getEnd().get().line;
            if(lineBegin<=lineNumber&&lineEnd>=lineEnd){
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
}
