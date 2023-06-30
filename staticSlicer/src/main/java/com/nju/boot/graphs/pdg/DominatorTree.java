package com.nju.boot.graphs.pdg;

import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;

import javax.swing.tree.TreeModel;
import java.util.ArrayList;
import java.util.List;

public class DominatorTree {
    TreeNode root;
    boolean built = false;
    public void build(CFG cfg){
        if(built)return;
        built = true;
        List<TreeNode> treeNodeList = new ArrayList<>();
        root = new TreeNode(cfg.getRootNode().get());
        treeNodeList.add(root);
        root.doms.add(root);
        for (GraphNode<?>graphNode:cfg.vertexSet()){
            treeNodeList.add(new TreeNode(graphNode));
        }
        for(TreeNode treeNode:treeNodeList){
            treeNode.doms.addAll(treeNodeList);
        }

        boolean changed = true;
        while(changed){
            changed = false;
            for(TreeNode treeNode:treeNodeList){
                if(treeNode == root)continue;


            }

        }

    }
    public List<TreeNode> getOr(){
        return null;
    }
    public class TreeNode{
        GraphNode<?> graphNode;
        TreeNode immediateDominator;
        List<TreeNode> doms = new ArrayList<>();
        TreeNode(GraphNode<?> g){
            this.graphNode = g;
        }
    }
}
