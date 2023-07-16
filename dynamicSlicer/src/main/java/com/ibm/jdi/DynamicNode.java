package com.ibm.jdi;

import com.nju.boot.nodes.GraphNode;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class DynamicNode {
    private GraphNode<?> GN;

    private Integer version;

    private Set<DynamicNode> Def;

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

    public boolean samePred(Set<DynamicNode> C){
        return Pred.equals(C);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGN(), getVersion());
    }
}
