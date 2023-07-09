package com.nju.boot.slicer;

import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.pdg.DominatorTree;
import com.nju.boot.graphs.pdg.PDG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.*;

public class DataFlowEquationSlicer {

    CFG cfg;

    PDG cdg;

    /**R[C](i) represents related variables in node i for slicing criterion C, using for tracking data dependencies */
    Map<GraphNode<?>, Set<String>> relevantVariables;

    /**S[C] represents the result of slicing */
    Set<GraphNode<?>> relevantStatements;

    /**B[C] represents branch statements, using for tracking control dependencies, the sign to stop the iteration */
    Set<GraphNode<?>> branchStatements;

    public DataFlowEquationSlicer(CFG cfg){
        this.cfg = cfg;
        assert cfg.isBuilt();
        this.cdg = new PDG();
        this.cdg.buildCDG(cfg);
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


    public void slice(SlicerCriterion slicerCriterion){
        InitializeRC0(slicerCriterion);
        InitializeSC0(slicerCriterion);
        System.out.println(relevantVariables);
        System.out.println(relevantStatements);
        branchStatements = new HashSet<>();
        int round = 0;
        while(true){
            System.out.println(round++);
            System.out.println(branchStatements);
            Set<GraphNode<?>> oldBranchStatements = new HashSet<>(branchStatements);
            for(GraphNode<?> branch: cfg.vertexSet()) {
                for (GraphNode<?> node : INFL(branch)) {
                    if (relevantStatements.contains(node)) {
                        branchStatements.add(branch);
                        break;
                    }
                }
            }
            if(branchStatements.equals(oldBranchStatements)){
                break;
            }
            for(GraphNode<?> node: cfg.vertexSet()){
               for(GraphNode<?> branch: branchStatements){
                   relevantVariables.get(node).addAll(getRC0(node, new SlicerCriterion(branch, cfg)));
               }
            }
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
    }

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
        System.out.println(node);
        System.out.println(result);
        return result;
    }




}
