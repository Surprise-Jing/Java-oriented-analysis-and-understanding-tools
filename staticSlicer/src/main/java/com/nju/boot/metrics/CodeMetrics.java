package com.nju.boot.metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedTypeParameterDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.nju.boot.edges.CallEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.callgraph.CallGraph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.Graphs;
import com.nju.boot.util.SlicerUtil;

import java.util.List;

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
        LineCounterVisitor lineCounterVisitor = new LineCounterVisitor();
        method.accept(lineCounterVisitor,null);
        return lineCounterVisitor.getCount();
    }
    /**根据方法的签名返回该方法调用方法的次数*/
    public int getTimesCalling(String methodSignature){
        return getTimesCalling(SlicerUtil.findMethodBySignature(graphs,methodSignature));
    }
    /**根据方法的ast树结点返回该方法调用方法的次数*/
    public int getTimesCalling(CallableDeclaration<?> method){
        CallGraph callGraph = graphs.getCallGraph();
        String methodSig = null;
        if(method instanceof MethodDeclaration){
            methodSig= ((MethodDeclaration) method).resolve().getQualifiedSignature();
        }
        else if(method instanceof ConstructorDeclaration){
            methodSig = ((ConstructorDeclaration) method).resolve().getQualifiedSignature();
        }
        if(methodSig == null){
            //方法不存在则返回-1
            return -1;
        }
        else{
            GraphNode<CallableDeclaration<?>> node =  callGraph.getSignatureToNodeMap().get(methodSig);
            if(node == null)
                return -1;
            else{
                int result = 0;
                for(Edge e: callGraph.outgoingEdgesOf(node)){
                    assert e instanceof CallEdge;
                    result += ((CallEdge)e).getTimes();
                }
                return result;
            }

        }
    }

    /**根据方法的签名返回该方法在文件中被调用的次数*/
    public int getTimesCalled(String methodSignature){
        return getTimesCalled(SlicerUtil.findMethodBySignature(graphs,methodSignature));
    }
    /**根据方法的ast树结点返回该方法在文件中被调用的次数*/
    public int getTimesCalled(CallableDeclaration<?> method){
        CallGraph callGraph = graphs.getCallGraph();
        String methodSig = null;
        if(method instanceof MethodDeclaration){
            methodSig= ((MethodDeclaration) method).resolve().getQualifiedSignature();
        }
        else if(method instanceof ConstructorDeclaration){
            methodSig = ((ConstructorDeclaration) method).resolve().getQualifiedSignature();
        }
        if(methodSig == null){
            //方法不存在则返回-1
            return -1;
        }
        else{
            GraphNode<CallableDeclaration<?>> node =  callGraph.getSignatureToNodeMap().get(methodSig);
            if(node == null)
                return -1;
            else{
                int result = 0;
                for(Edge e: callGraph.incomingEdgesOf(node)){
                    assert e instanceof CallEdge;
                    result += ((CallEdge)e).getTimes();
                }
                return result;
            }

        }
    }
    /**根据方法的签名返回该方法的入参个数*/
    public int getNumOfParameters(String methodSignature){
        return getNumOfParameters(SlicerUtil.findMethodBySignature(graphs,methodSignature));
    }
    /**根据方法的ast树结点返回该方法的入参个数*/
    public int getNumOfParameters(CallableDeclaration<?> method){
        return method.getParameters().size();

    }
}
