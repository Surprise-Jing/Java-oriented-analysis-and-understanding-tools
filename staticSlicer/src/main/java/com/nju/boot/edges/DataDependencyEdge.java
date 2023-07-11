package com.nju.boot.edges;

import java.util.HashSet;
import java.util.Set;

public class DataDependencyEdge extends Edge{
    Set<String> variables = new HashSet<>();
    /**加入数据依赖边相关的变量信息*/
    public void addDependentVariable(String variable){
        variables.add(variable);
    }
    public void addDependentVaraibles(Set<String>variables){
        this.variables.addAll(variables);
    }
    public Set<String> getDependentVariables(){
        return variables;
    }
}
