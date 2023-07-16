package com.nju.boot.graphs.dependencegraph;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.DummyEdge;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.printer.DominatorTreePrinter;
import com.nju.boot.graphs.printer.GraphPrinter;
import com.nju.boot.nodes.GraphNode;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 支配树，可以获得控制流图中节点之间的支配和被支配关系
 */
public class DominatorTree extends Graph<MethodDeclaration> {
    public DominatorTree(CFG cfg){
        this.cfg = cfg;
    }

    /**
     * 从其创建支配树的控制流图
     */
    CFG cfg;
    boolean built = false;
    private int nodeID = 0;
    /**
     * 键：节点
     * 值：该节点的支配者
     */
    Map<GraphNode<?>,GraphNode<?>>doms = new HashMap<>();
    /**
     * 节点-编号的map
     */
    Map<GraphNode<?>,Integer> nodeIndexMap = new HashMap<>();
    /**
     * 编号-节点的map
     */
    Map<Integer,GraphNode<?>> indexNodeMap = new HashMap<>();

    /**

     * @param b1
     * @param b2
     * @return 节点b1，b2的最近共同支配节点
     */
    public GraphNode<?> intersect(GraphNode<?>b1,GraphNode<?>b2){
        GraphNode<?>f1 = b1;
        GraphNode<?>f2 = b2;
        while(f1!=f2){
            while(nodeIndexMap.get(f1)<nodeIndexMap.get(f2))//f1比f2后处理
                f1 = doms.get(f1);
            while(nodeIndexMap.get(f2)<nodeIndexMap.get(f1)){//f1比f2先处理
                f2 = doms.get(f2);
            }
        }
        return f1;
    }

    /**
     * 深度遍历控制流图
     */
    public void DFS(){
        Set<GraphNode<?>> visited=  new HashSet<>();
        GraphNode<?>root = cfg.getRootNode().get();
        DFS(root,visited);

    }

    /**
     * 深度遍历控制流图，为节点按后序编号
     * @param n
     * @param visited 遍历过的节点集合
     */
    public void DFS(GraphNode<?> n,Set<GraphNode<?>>visited){
        visited.add(n);
            for(Edge e:cfg.outgoingEdgesOf(n)){
                GraphNode<?>target = cfg.getEdgeTarget(e);
                if(!visited.contains(target))
                DFS(target,visited);
        }
        //postOrder.push(n);
        //给节点后序编号
        int id = ++nodeID;
        nodeIndexMap.put(n,id);
        indexNodeMap.put(id,n);
    }

    /**
     * 该支配树的建造方法
     * @return 建造好的支配树
     */

    public DominatorTree build(){
        if(built)return this;
        built = true;
        cfg.vertexSet().stream().forEach(this::addVertex);

        DFS();
        //为节点进行postorder编号
        GraphNode<?> startNode = indexNodeMap.get(nodeID);
        doms.put(startNode,startNode);

        boolean changed = true;
        while(changed){
            changed = false;
            for(int nodeIndex = nodeID;nodeIndex>=1;nodeIndex--){
                GraphNode<?> b = indexNodeMap.get(nodeIndex);
                if(b == startNode) continue;
                //该节点的所有直接前驱


                List<GraphNode<?>> predecessors = cfg.incomingEdgesOf(b).stream()
                        .map(edge -> cfg.getEdgeSource(edge))
                        .collect(Collectors.toList());

                GraphNode<?>new_idom = null;

                //选取一个处理过的节点为支配节点的默认值
                for(GraphNode<?> predecessor:predecessors){
                    if(doms.get(predecessor)!=null){
                        new_idom = predecessor;
                        break;
                    }
                }
                //剩下的直接前驱
                predecessors.remove(new_idom);

                for (GraphNode<?> predecessor:predecessors){
                    //如果该节点有支配节点，即被处理过
                    if(doms.get(predecessor)!=null){
                        //新的支配节点是当前得到的支配节点和
                        //在处理的前驱节点的支配节点的
                        //两者共同的支配节点
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
            if(doms.get(vertex)!=null&&doms.get(vertex)!=vertex){
                addEdge(doms.get(vertex),vertex,new DummyEdge());
            }

        }
        return this;
    }

    /**
     *
     * @param a
     * @param b
     * @return a是否支配b
     */
    public boolean dominates(GraphNode<?>a,GraphNode<?>b){

        if(containsEdge(a,b))return true;
        else{
            GraphNode<?> parentOfB = getParent(b);
            if(parentOfB == null) return false;
            return dominates(a,parentOfB);
        }
    }

    /**
     *
     * @param node
     * @return node在支配树中的直接父结点，
     * 如果node为根节点，则为null值
     */
    public GraphNode<?>getParent(GraphNode<?>node){
        Set<Edge> incomingEdges = this.incomingEdgesOf(node);
        assert (incomingEdges.size()<=1);
        if(incomingEdges.isEmpty())return null;
        Edge incomingEdge = incomingEdges.stream().collect(Collectors.toList()).get(0);
        GraphNode<?> parent = getEdgeSource(incomingEdge);
        return parent;
    }

    /**
     * 向writer中写入dot文件的支配树
     * @param writer
     */
    @Override
    protected void writeAsDot(Writer writer) {
        new DominatorTreePrinter(this,writer, GraphPrinter.Format.DOT).print();
    }
}
