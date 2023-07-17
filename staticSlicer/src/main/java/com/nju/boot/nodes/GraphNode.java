package com.nju.boot.nodes;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.Statement;
import com.nju.boot.graphs.Graph;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;



/**
 *表示各种图中的节点（包括CFG、PDG、CG）
 * 包括其AST表示和与同一图中其他节点的连接。
 * 节点不可变的
 * @param <N> 该节点表示的AST的类型。
*/
public class GraphNode<N extends Node> {

    /** 节点ID号，依次生成 */
    private final int id;

    /** 节点label标签值，显示在生成图上 */
    private final String instruction;

    /** 节点对应AST树节点 */
    private final N astNode;

    // 函数声明的变量集合，用于系统依赖图
    //private final Set<String> declaredVariables;

    /** 定义的变量集合 */
    private final Set<String> definedVariables;

    /** 使用的变量集合 */
    private final Set<String> usedVariables;

    GraphNode(int id, String instruction, @NotNull N astNode,
              Collection<String> definedVariables,
              Collection<String> usedVariables) {
        this.id = id;
        this.instruction = instruction;
        this.astNode = astNode;

        this.definedVariables = new HashSet<>(definedVariables);
        this.usedVariables = new HashSet<>(usedVariables);
    }
    public GraphNode(int id, String instruction, @NotNull N astNode) {
        this(
                id,
                instruction,
                astNode,
                new HashSet<>(0),
                new HashSet<>(0)
        );
        if(id != 0 && id != 1&&astNode!=null)
            extractVariables(astNode);
    }

    private void extractVariables(N astNode) {
        new VariableVisitor<>(this).extract(astNode);
    }


    public int getId() {
        return id;
    }

    public String toString() {
        return String.format("GraphNode{id: %s, instruction: '%s', astNodeType: %s}",
                getId(),
                getInstruction(),
                getAstNode().getClass().getSimpleName()
        );
    }

    public N getAstNode() {
        return astNode;
    }



    public void addDefinedVariable(String variable) {
        definedVariables.add(variable);
    }

    public void addUsedVariable(String variable) {
        usedVariables.add(variable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof GraphNode))
            return false;

        GraphNode<?> other = (GraphNode<?>) o;

        return Objects.equals(getId(), other.getId())
                && Objects.equals(getInstruction(), other.getInstruction())
                && Objects.equals(astNode, other.astNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInstruction(), getAstNode());
    }

    public Set<String> getDefinedVariables() {
        return definedVariables;
    }

    public Set<String> getUsedVariables() {
        return usedVariables;
    }

    public String getInstruction() {
        return instruction;
    }


}
