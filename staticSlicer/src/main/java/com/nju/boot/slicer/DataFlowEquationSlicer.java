package com.nju.boot.slicer;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.exceptions.MethodNotFoundException;
import com.nju.boot.slicer.printer.SelectivePrettyPrinter;
import com.nju.boot.util.GraphsUtil;

import java.util.*;

public class DataFlowEquationSlicer extends  AbstractSlicer{
    Graphs graphs;

    CFG cfg;

    CDG cdg;

    /**R[C](i) represents related variables in node i for slicing criterion C, using for tracking data dependencies */
    Map<GraphNode<?>, Set<String>> relevantVariables;

    /**S[C] represents the result of slicing */
    Set<GraphNode<?>> relevantStatements;

    /**B[C] represents branch statements, using for tracking control dependencies, the sign to stop the iteration */
    Set<GraphNode<?>> branchStatements;

    public DataFlowEquationSlicer(String fileName) {
        graphs = new Graphs(fileName);
    }

    public DataFlowEquationSlicer(Graphs graphs){
        this.graphs = graphs;
    }


    public boolean isMarked(GraphNode<?> node){
        return relevantStatements.contains(node);
    }

    void InitializeRC0(SlicerCriterion slicerCriterion){
        relevantVariables = new HashMap<>();
        Map<GraphNode<?>, Integer> visited = new HashMap<>();
        for(GraphNode<?> node: cfg.vertexSet()){
            relevantVariables.put(node, new HashSet<>());
            visited.put(node, 0);
        }
        List<GraphNode<?>> nodes = new LinkedList<>(slicerCriterion.getNodes());
        while(true)
        {
            if(nodes.isEmpty()){
                break;
            }
            GraphNode<?> node = nodes.remove(0);
            if(visited.get(node) > 2*cfg.inDegreeOf(node))
                continue;
            relevantVariables.get(node).addAll(getRC0(node, slicerCriterion));
            for(Edge edge: cfg.incomingEdgesOf(node)){
                GraphNode<?> src = cfg.getEdgeSource(edge);
                nodes.add(src);
            }
            visited.put(node, visited.get(node)+1);
        }
    }

    /**
     * 根据当前节点所有的后续节点，遍历获得变量的相关变量信息
     * 若变量在当前节点未被定义，且在后续节点的变量相关信息里
     * 若变量在当前节点被使用，则不继承后续变量信息
     * @param node
     * @param slicerCriterion 相应的切片准则
     * @return
     */
    Set<String> getRC0(GraphNode<?> node, SlicerCriterion slicerCriterion){
        if(slicerCriterion.getNodes().contains(node)){
            return slicerCriterion.getVariable();
        }
        if(node.getId() == 0 || node.getId() == 1){
            return new HashSet<>();
        }
        Set<String> result = new HashSet<>();
        for(Edge edge: cfg.outgoingEdgesOf(node)){
            GraphNode<?> target = cfg.getEdgeTarget(edge);
            // USE(n), when there is a v in both DEF(n) and R[0][C](m)
            Set<String> targetRC0;
            if(target.getId() > node.getId()){
                targetRC0 = relevantVariables.get(target);
            }
            else{
                targetRC0 = getRC0(target, slicerCriterion);
            }
            if(!Collections.disjoint(targetRC0, node.getDefinedVariables())){
                result.addAll(node.getUsedVariables());
            }
            // v is not in DEF(n) and v is in R[0][C](m)
            for(String s: targetRC0){
                if(!node.getDefinedVariables().contains(s)){
                    result.add(s);
                }
            }
        }
        return result;
    }

    void InitializeSC0(SlicerCriterion slicerCriterion){
        relevantStatements = new HashSet<>();
        for(Edge edge: cfg.edgeSet()){
            GraphNode<?> src = cfg.getEdgeSource(edge);
            GraphNode<?> target = cfg.getEdgeTarget(edge);
            if(!Collections.disjoint(relevantVariables.get(target), src.getDefinedVariables())){
                relevantStatements.add(src);
            }
        }
    }
    public AbstractSlicer slice(int lineNumber,String variableName) {
        if(!isSlicable(lineNumber,variableName))
            throw new MethodNotFoundException();
        CallableDeclaration<?> tarMethod = GraphsUtil.findMethodByLineNumber(graphs.getCu(),lineNumber);
        this.cfg = graphs.getCFG(tarMethod);
        this.cdg = graphs.getCDG(tarMethod);
        Set<String> variables = new HashSet<>();
        variables.add(variableName);
        return slice(new SlicerCriterion(variables,lineNumber,cfg));
    }

    @Override
    public Set<GraphNode<?>> getSlicedGraphNode() {
        return  relevantStatements;
    }

    @Override
    public boolean isSlicable(int lineNumber, String variable)  {
        return GraphsUtil.getNodeBy(graphs,lineNumber,variable)!=null;

    }

    @Override
    public String getResultCode() {
        return new SelectivePrettyPrinter(getSlicedAstNode()).print(graphs.getCu());
    }

    private AbstractSlicer slice(SlicerCriterion slicerCriterion){
        //初始化切片准则起始变量
        InitializeRC0(slicerCriterion);
        //初始化当前流程中程序切片的最终结果
        InitializeSC0(slicerCriterion);
        //System.out.println(relevantVariables);
        //System.out.println(relevantStatements);
        branchStatements = new HashSet<>();
        int round = 0;
        while(true){
            //迭代次数round
            //System.out.println(round++);
            //System.out.println(branchStatements);
            Set<GraphNode<?>> oldBranchStatements = new HashSet<>(branchStatements);
            //更新分支语句，根据当前分支语句中节点的控制依赖关系，判断是否有新的节点加入分支语句
            for(GraphNode<?> branch: cfg.vertexSet()) {
                for (GraphNode<?> node : INFL(branch)) {
                    if (relevantStatements.contains(node)) {
                        branchStatements.add(branch);
                        break;
                    }
                }
            }
            //分支语句集合不再更新，循环结束
            if(branchStatements.equals(oldBranchStatements)){
                break;
            }
            //根据分支语句集合，更新每个节点的相关变量
            for(GraphNode<?> node: cfg.vertexSet()){
               for(GraphNode<?> branch: branchStatements){
                   relevantVariables.get(node).addAll(getRC0(node, new SlicerCriterion(branch, cfg)));
               }
            }
            //System.out.println(relevantVariables);
            //根据分支集合重新寻找当前迭代中程序切片结果
            relevantStatements.clear();
            for(Edge edge: cfg.edgeSet()){
                GraphNode<?> src = cfg.getEdgeSource(edge);
                GraphNode<?> target = cfg.getEdgeTarget(edge);
                if(!Collections.disjoint(relevantVariables.get(target), src.getDefinedVariables())){
                    relevantStatements.add(src);
                }
            }
            relevantStatements.addAll(branchStatements);
        }
        //Slicing node use variables
        for(GraphNode<?> node: slicerCriterion.getNodes()){
            if(!Collections.disjoint(node.getDefinedVariables(), slicerCriterion.getVariable()) ||
                    !Collections.disjoint(node.getUsedVariables(), slicerCriterion.getVariable())){
                relevantStatements.add(node);
            }
        }
        relevantStatements.add(cfg.getRootNode().get());
        return this;
    }

    /**
     * INFL(b)表示从b开始到距离它最近的后向支配语句之间的路径上除去端点以外所有语句的集合
     * 其直接后驱>=2时才不为空，否则为空集
     * @param node
     * @return 控制依赖于b的语句集合
     */
    Set<GraphNode<?>> INFL(GraphNode<?> node){
        Set<GraphNode<?>> result = new HashSet<>();
        if(cfg.outDegreeOf(node) < 2){
            return result;
        }
        for(Edge edge:cdg.outgoingEdgesOf(node)){
            result.add(cdg.getEdgeTarget(edge));
        }
        /*
        DijkstraShortestPath<GraphNode<?>, Edge> dijkstraShortestPath = new DijkstraShortestPath<>(cfg);
        for(Edge edge: cdg.outgoingEdgesOf(node)){
            GraphNode<?> target = cdg.getEdgeTarget(edge);
            List<GraphNode<?>> nodes = dijkstraShortestPath.getPath(node, target).getVertexList();
            nodes.remove(target);
            result.addAll(nodes);
        }
         */
        //System.out.println(node);
        //System.out.println(result);
        return result;
    }




}
