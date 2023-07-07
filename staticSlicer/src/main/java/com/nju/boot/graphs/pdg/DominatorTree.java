package com.nju.boot.graphs.pdg;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.DummyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import javax.swing.tree.TreeModel;
import java.lang.reflect.Method;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class DominatorTree extends Graph<MethodDeclaration> {
    public DominatorTree(CFG cfg){
        this.cfg = cfg;
    }
    CFG cfg;
    boolean built = false;

    Map<GraphNode<?>,GraphNode<?>>doms = new HashMap<>();
    Stack<GraphNode<?>>postOrder = new Stack<>();
    List<GraphNode<?>>reversedPostOrder = new ArrayList<>();
    public GraphNode<?> intersect(GraphNode<?>b1,GraphNode<?>b2){
        GraphNode<?>f1 = b1;
        GraphNode<?>f2 = b2;
        while(f1!=f2){
            while(getIndex(f1)>getIndex(f2))
                f1 = doms.get(f1);
            while(getIndex(f2)>getIndex(f1)){
                f2 = doms.get(f2);
            }
        }
        return f1;
    }
    public int getIndex(GraphNode<?> b){
        for (int i = 0;i<reversedPostOrder.size();i++){
            if(b == reversedPostOrder.get(i)){
                return i;
            }
        }
        return -1;
    }
    public void DFS(){
        Set<GraphNode<?>> visited=  new HashSet<>();
        GraphNode<?>root = cfg.getRootNode().get();
        DFS(root,visited);

    }
    public void DFS(GraphNode<?> n,Set<GraphNode<?>>visited){
        visited.add(n);
        for(Edge e:cfg.outgoingEdgesOf(n)){
            GraphNode<?>target = cfg.getEdgeTarget(e);
            if(!visited.contains(target))
                DFS(target,visited);
        }
        postOrder.push(n);
    }

    public DominatorTree build(){
        if(built)return this;
        built = true;
        cfg.vertexSet().stream().forEach(this::addVertex);

        GraphIterator<GraphNode<?>, Edge> it = new DepthFirstIterator<>(cfg);

        DFS();
        while(!postOrder.isEmpty()){
            GraphNode<?> g = postOrder.pop();
            reversedPostOrder.add(g);
            doms.put(g,null);

        }
        doms.put(reversedPostOrder.get(0),reversedPostOrder.get(0));

        boolean changed = true;
        while(changed){
            changed = false;
            for(GraphNode<?> b:reversedPostOrder){
                if(b == reversedPostOrder.get(0))continue;
                List<GraphNode<?>> predecessors = cfg.incomingEdgesOf(b).stream()
                        .map(edge -> cfg.getEdgeSource(edge))
                        .collect(Collectors.toList());

                GraphNode<?>new_idom = null;
                for(GraphNode<?> predecessor:predecessors){
                    if(doms.get(predecessor)!=null){
                        new_idom = predecessor;
                        break;
                    }
                }
                predecessors.remove(new_idom);
                for (GraphNode<?> predecessor:predecessors){
                    if(doms.get(predecessor)!=null){
                        new_idom = intersect(new_idom,predecessor);
                    }
                }
                if(doms.get(b) != new_idom){
                    doms.put(b,new_idom);
                    changed = true;
                }


            }
        }

        for(GraphNode<?>vertex :doms.keySet()){
            if(doms.get(vertex)!=null&&doms.get(vertex)!=vertex)
                addEdge(doms.get(vertex),vertex,new DummyEdge());
        }
        return this;
    }
    /**returns if a dominates b*/
    public boolean dominates(GraphNode<?>a,GraphNode<?>b){
        if(containsEdge(a,b))return true;
        else{
            GraphNode<?> parentOfB = getParent(b);
            if(parentOfB == null) return false;
            return dominates(a,parentOfB);
        }
    }
    public GraphNode<?>getParent(GraphNode<?>node){
        Set<Edge> incomingEdges = this.incomingEdgesOf(node);
        assert (incomingEdges.size()<=1);
        if(incomingEdges.isEmpty())return null;
        Edge incomingEdge = incomingEdges.stream().collect(Collectors.toList()).get(0);
        GraphNode<?> parent = getEdgeSource(incomingEdge);
        return parent;
    }

}
