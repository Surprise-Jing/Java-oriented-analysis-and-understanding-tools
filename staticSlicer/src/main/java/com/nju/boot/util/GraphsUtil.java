package com.nju.boot.util;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.printer.XmlPrinter;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.nodes.GraphNode;
import io.swagger.models.auth.In;

import java.util.Collections;
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
    public static Set<GraphNode<?>> findNodesByLineNumber(Graphs graphs, int lineNumber){
        return null;
    }

    public static CallableDeclaration<?> findMethodBySignature(Graphs graphs, String signature){

        return graphs.getCallGraph().getSignatureToNodeMap().get(signature).getAstNode();
    }
    public static String astNodeToXml(CompilationUnit compilationUnit){
        XmlPrinter xmlPrinter = new XmlPrinter(true);
        return xmlPrinter.output(compilationUnit);

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
}
