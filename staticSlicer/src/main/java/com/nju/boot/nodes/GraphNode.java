package com.nju.boot.nodes;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.Statement;
import com.nju.boot.graphs.Graph;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GraphNode<N extends Node> {

    private final int id;
    private final String instruction;
    private final N astNode;

    //private final Set<String> declaredVariables;
    private final Set<String> definedVariables;
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
