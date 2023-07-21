package com.ibm.jdi;

import com.nju.boot.nodes.GraphNode;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class DynamicNode {
//    对应静态图结点
    private GraphNode<?> GN;
//    动态结点版本号
    private Integer version;
//    数据依赖的动态结点
    private Set<DynamicNode> Def;
//    控制依赖的动态结点
    private Set<DynamicNode> Pred;

    DynamicNode(Set<DynamicNode> def, Set<DynamicNode> pred, GraphNode<?> GN, int version){
        this.Def = new HashSet<>(def);
        this.Pred = new HashSet<>(pred);
        this.GN = GN;
        this.version = version;
    }
    public boolean sameDef(Set<DynamicNode> D){
        return Def.equals(D);
    }
//    判断数据依赖是否相同
    public boolean samePred(Set<DynamicNode> C){
        return Pred.equals(C);
    }
//    判断数据依赖是否相同
    @Override
//    设置哈希码
    public int hashCode() {
        return Objects.hash(getGN(), getVersion());
    }
}
