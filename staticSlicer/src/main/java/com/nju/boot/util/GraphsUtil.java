package com.nju.boot.util;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.printer.XmlPrinter;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

import java.util.Set;

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
}
