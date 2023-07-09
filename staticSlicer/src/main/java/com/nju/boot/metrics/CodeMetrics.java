package com.nju.boot.metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.slicer.Graphs;
import com.nju.boot.util.SlicerUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeMetrics {
    Graphs graphs;

    public CodeMetrics(Graphs graphs) {
        this.graphs = graphs;
    }
    /**计算方法的圈复杂度*/
    public int getCyclomaticComplexity (String methodSignature){
        return getCyclomaticComplexity(SlicerUtil.findMethodBySignature(graphs,methodSignature));
    }
    /**计算方法的圈复杂度*/
    public int getCyclomaticComplexity(CallableDeclaration<?> callableDeclaration){
        CFG targetCFG = graphs.getCFG(callableDeclaration);
        int numEdges = targetCFG.edgeSet().size(),
                numVertices = targetCFG.vertexSet().size();
        return numEdges-numVertices+2;
    }
    /**计算所有类的最大继承深度*/
    public int getMaxDepthOfInheritance(){
        int maxDepth = 0;
        CompilationUnit compilationUnit = graphs.getCu();
        for(TypeDeclaration<?> typeDeclaration:compilationUnit.getTypes()){
            ResolvedReferenceTypeDeclaration resolvedReferenceTypeDeclaration = typeDeclaration.resolve();
            if(!resolvedReferenceTypeDeclaration.isInterface()){
                int depth = getDepthOfInheritance(resolvedReferenceTypeDeclaration);
                if(depth>maxDepth)maxDepth = depth;
            }
        }
        return maxDepth;
    }
    public int getDepthOfInheritance(ResolvedReferenceTypeDeclaration resolvedReferenceTypeDeclaration){
        ResolvedReferenceType parentClassType = null;
        if(!resolvedReferenceTypeDeclaration.isInterface()){
            List<ResolvedReferenceType>ancestors = resolvedReferenceTypeDeclaration.getAncestors();
            for(ResolvedReferenceType ancestor:ancestors){
                if(!ancestor.getTypeDeclaration().get().isInterface()){
                    parentClassType = ancestor;
                    break;
                }
            }
        }
        if(parentClassType == null)
            return 0;
        return 1+getDepthOfInheritance(parentClassType.getTypeDeclaration().get());
    }
    public int getLineOfMethod(String methodSignature){
        return getLineOfMethod(SlicerUtil.findMethodBySignature(graphs,methodSignature)) ;
    }
    public int getLineOfMethod(CallableDeclaration<?> method){
        method.accept(new LineCounterVisitor(),null);
        return 0;
    }

}
