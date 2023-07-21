package com.nju.boot.metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.nju.boot.edges.CallEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.callgraph.CallGraph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.util.GraphsUtil;

import java.util.List;

/**
 * 代码度量类
 */
public class CodeMetrics {
    Graphs graphs;

    /**
     *
     * @param fileName 需要进行度量的文件路径
     */
    public CodeMetrics(String fileName){
        this.graphs = new Graphs(fileName);
    }

    /**
     *
     * @param graphs 需要进行度量的代码抽象模型信息
     */
    public CodeMetrics(Graphs graphs) {
        this.graphs = graphs;
    }
    /**
     * 计算方法的圈复杂度
     * */
    public int getCyclomaticComplexity (String methodSignature){
        return getCyclomaticComplexity(GraphsUtil.findMethodBySignature(graphs,methodSignature));
    }
    /**
     * 计算方法的圈复杂度
     * */
    public int getCyclomaticComplexity(CallableDeclaration<?> callableDeclaration){
        CFG targetCFG = graphs.getCFG(callableDeclaration);
        int numEdges = targetCFG.edgeSet().size(),
                numVertices = targetCFG.vertexSet().size();
        return numEdges-numVertices+2;
    }
    /**
     * 计算所有类的最大继承深度
     * */
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

    /**
     *
     * @param resolvedReferenceTypeDeclaration
     * @return 指定类的继承深度
     */
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

    /**
     *
     * @param methodSignature
     * @return 执行函数的代码行数
     */
    public int getLinesCodeOfMethod(String methodSignature){
        return getLinesCodeOfMethod(GraphsUtil.findMethodBySignature(graphs,methodSignature)) ;
    }

    /**
     *
     * @param method
     * @return 指定函数的代码行数
     */
    public int getLinesCodeOfMethod(CallableDeclaration<?> method){
        return new LineCounter(method.toString()).getLinesOfCode();
    }

    /**
     *
     * @param methodSignature
     * @return  指定函数的注释行数
     */

    public int getLinesCommentOfMethod(String methodSignature){
        return getLinesCommentOfMethod(GraphsUtil.findMethodBySignature(graphs,methodSignature)) ;
    }
    /**
     *
     * @param method
     * @return  指定函数的注释行数
     */
    public int getLinesCommentOfMethod(CallableDeclaration<?> method){
        return new LineCounter(method.toString()).getLinesOfComment();
    }

    /**
     *
     * @param methodSignature
     * @return 指定函数的空白行数
     */

    public int getLinesBlankOfMethod(String methodSignature){
        return getLinesBlankOfMethod(GraphsUtil.findMethodBySignature(graphs,methodSignature)) ;
    }

    /**
     *
     * @param method
     * @return 指定函数的空白行数
     */
    public int getLinesBlankOfMethod(CallableDeclaration<?> method){
        return new LineCounter(method.toString()).getLinesOfBlanks();
    }

    /**
     *
     * @return 整个代码行数
     */
    public int getLinesCode(){
        return new LineCounter(graphs.getCu().toString()).getLinesOfCode();
    }

    /**
     *
     * @return 整个注释行数
     */

    public int getLinesComment(){
        return new LineCounter(graphs.getCu().toString()).getLinesOfComment();
    }

    /**
     *
     * @return 整个空白行数
     */

    public int getLinesBlank(){
        return new LineCounter(graphs.getCu().toString()).getLinesOfBlanks();
    }

    /**
     * 根据方法的签名返回该方法调用方法的次数
     * */
    public int getTimesCalling(String methodSignature){
        return getTimesCalling(GraphsUtil.findMethodBySignature(graphs,methodSignature));
    }
    /**
     * 根据方法的ast树结点返回该方法调用方法的次数
     * */
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
                //callgraph的边有调用次数的信息
                int result = 0;
                for(Edge e: callGraph.outgoingEdgesOf(node)){
                    assert e instanceof CallEdge;
                    /**
                     * 结果加上出边的调用次数信息
                     */
                    result += ((CallEdge)e).getTimes();
                }
                return result;
            }

        }
    }

    /**
     * 根据方法的签名返回该方法在文件中被调用的次数
     * */
    public int getTimesCalled(String methodSignature){
        return getTimesCalled(GraphsUtil.findMethodBySignature(graphs,methodSignature));
    }
    /**
     * 根据方法的ast树结点返回该方法在文件中被调用的次数
     * */
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
                    /**结果加上入边的调用次数信息*/
                    result += ((CallEdge)e).getTimes();
                }
                return result;
            }

        }
    }
    /**
     * 根据方法的签名返回该方法的入参个数
     * */
    public int getNumOfParameters(String methodSignature){
        return getNumOfParameters(GraphsUtil.findMethodBySignature(graphs,methodSignature));
    }
    /**
     * 根据方法的ast树结点返回该方法的入参个数
     * */
    public int getNumOfParameters(CallableDeclaration<?> method){
        return method.getParameters().size();

    }
}
