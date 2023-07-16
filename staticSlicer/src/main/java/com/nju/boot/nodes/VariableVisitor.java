package com.nju.boot.nodes;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

enum Var{
    DEFINED, USED;
    public Var type(Var var){
        if(var == DEFINED || this == DEFINED){
            return DEFINED;
        }
        else{
            return USED;
        }
    }
}

public class VariableVisitor<N extends Node> extends VoidVisitorAdapter<Var> {

    private GraphNode<N> node;

    VariableVisitor(GraphNode<N> node){
        this.node = node;
    }

    public void extract(Node n){
        n.accept(this, Var.USED);
    }

    @Override
    public void visit(ArrayAccessExpr arrayAccessExpr, Var var){
        // Array brackets []
        // Logger.log("On ArrayAccessExpr: [" + n + "]");
        arrayAccessExpr.getName().accept(this, var.type(Var.USED));
        arrayAccessExpr.getIndex().accept(this, var.type(Var.USED));
    }

    @Override
    public void visit(ArrayCreationExpr arrayCreationExpr, Var var){
        // Logger.log("On ArrayCreationExpr: " + n);
        arrayCreationExpr.getInitializer().ifPresent(arrayInitializerExpr -> arrayInitializerExpr.accept(this, var.type(Var.USED)));
    }

    @Override
    public void visit(ArrayInitializerExpr arrayInitializerExpr, Var var){
        // Logger.log("On ArrayInitializerExpr: " + n);
        for(Expression expression: arrayInitializerExpr.getValues()){
            expression.accept(this, var.type(Var.USED));
        }
    }

    @Override
    public void visit(AssignExpr assignExpr, Var var){
        // Logger.log("On AssignExpr: [" + n + "]");

        // if +=, *=, -= ...
        if(assignExpr.getOperator() != AssignExpr.Operator.ASSIGN){
            assignExpr.getTarget().accept(this, var.type(Var.USED));
        }
        assignExpr.getTarget().accept(this, var.type(Var.DEFINED));
        assignExpr.getValue().accept(this, var.type(Var.USED));
    }

    @Override
    public void visit(BinaryExpr binaryExpr, Var var){
        // Logger.log("On BinaryExpr: [" + n + "]");
        binaryExpr.getLeft().accept(this, var.type(Var.USED));
        binaryExpr.getRight().accept(this, var.type(Var.USED));
    }

    @Override
    public void visit(CastExpr castExpr, Var var){
        // Logger.log("On CastExpr: [" + n + "]");
        castExpr.getExpression().accept(this, var.type(Var.USED));
    }

    @Override
    public void visit(ConditionalExpr conditionalExpr, Var var){
        // Logger.log("On ConditionalExpr: [" + n + "]");
        conditionalExpr.getCondition().accept(this, var.type(Var.USED));
        conditionalExpr.getThenExpr().accept(this, var.type(Var.USED));
        conditionalExpr.getElseExpr().accept(this, var.type(Var.USED));
    }

    @Override
    public void visit(EnclosedExpr enclosedExpr, Var var){
        // Logger.log("On EnclosedExpr: [" + n + "]");
        enclosedExpr.getInner().accept(this, var.type(Var.USED));
    }

    @Override
    public void visit(FieldAccessExpr fieldAccessExpr, Var var){
        // Logger.log("On FieldAccessExpr: [" + n + "]");
        fieldAccessExpr.getScope().accept(this, var.type(Var.USED));
    }

    @Override
    public void visit(InstanceOfExpr instanceOfExpr, Var var){
        // Logger.log("On instanceOfExpr: " + n);
        instanceOfExpr.getExpression().accept(this, var.type(Var.USED));
    }

    @Override
    public void visit(MethodCallExpr methodCallExpr, Var var){
        // Logger.log("On MethodCallExpr: [" + n + "]");
        methodCallExpr.getScope().ifPresent(expression -> expression.accept(this, var.type(Var.USED)));
        for(Expression expression: methodCallExpr.getArguments()){
            expression.accept(this, var.type(Var.USED));
        }
    }

    @Override
    public void visit(NameExpr nameExpr, Var var){
        // Logger.log("On nameExpr: " + n);
        String variable = nameExpr.getNameAsString();
        if(!variable.equals("System")){
            switch (var){
                case USED: node.addUsedVariable(variable); break;
                case DEFINED: node.addDefinedVariable(variable); break;
                default: break;
            }
        }
    }

    @Override
    public void visit(UnaryExpr unaryExpr, Var var){
        // Logger.log("On unaryStmt: " + n);
        unaryExpr.getExpression().accept(this, var.type(Var.USED));
        //只有自增自减重新对变量赋值
        if(unaryExpr.getOperator() == UnaryExpr.Operator.PREFIX_DECREMENT||
                unaryExpr.getOperator() == UnaryExpr.Operator.PREFIX_INCREMENT||
                unaryExpr.getOperator() == UnaryExpr.Operator.POSTFIX_INCREMENT||
                unaryExpr.getOperator() == UnaryExpr.Operator.POSTFIX_DECREMENT
        )
        unaryExpr.getExpression().accept(this, var.type(Var.DEFINED));
    }

    @Override
    public void visit(VariableDeclarationExpr variableDeclarationExpr, Var var){
        // Logger.log("On VariableDeclarationExpr: [" + n + "]");
        for(VariableDeclarator variableDeclarator: variableDeclarationExpr.getVariables()){
            variableDeclarator.getNameAsExpression().accept(this, var.type(Var.DEFINED));
            variableDeclarator.getInitializer().ifPresent(
                    expression -> expression.accept(this, var.type(Var.USED))
            );
        }
    }

    @Override
    public void visit(ExpressionStmt expressionStmt, Var var){
        // Logger.log("On ExpressionStmt: " + n);
        expressionStmt.getExpression().accept(this, var);
    }

    @Override
    public void visit(IfStmt ifStmt, Var var){
        // Logger.log("On ifStmt: " + n);
        ifStmt.getCondition().accept(this, Var.USED);
    }

    @Override
    public void visit(SwitchEntry switchEntry, Var var){
        // Logger.log("On switchEntry: " + n);
        if(!switchEntry.getLabels().isEmpty()){
            switchEntry.getLabels().get(0).accept(this, Var.USED);
        }
    }

    @Override
    public void visit(SwitchStmt switchStmt, Var var){
        // Logger.log("On switchStmt: " + n);
        switchStmt.getSelector().accept(this, Var.USED);
    }

    @Override
    public void visit(WhileStmt whileStmt, Var var){
        // Logger.log("On whileStmt: " + n);
        whileStmt.getCondition().accept(this, Var.USED);
    }

    @Override
    public void visit(DoStmt doStmt, Var var){
        // Logger.log("On doStmt: " + n);
        doStmt.getCondition().accept(this, Var.USED);
    }

    @Override
    public void visit(ForStmt forStmt, Var var){
        // Logger.log("On forStmt: " + n);
        // n.getInitialization().forEach(stmt -> stmt.accept(this, VariableAction.Actions.USE));
        forStmt.getCompare().ifPresent(expression -> expression.accept(this, Var.USED));
    }

    @Override
    public void visit(ForEachStmt forEachStmt, Var var){
        // Logger.log("On forEachStmt: " + n);
        forEachStmt.getIterable().accept(this, Var.USED);
        for(VariableDeclarator variableDeclarator: forEachStmt.getVariable().getVariables()){
            variableDeclarator.getNameAsExpression().accept(this, Var.DEFINED);
        }
    }

    @Override
    public void visit(ReturnStmt returnStmt, Var var){
        // Logger.log("On returnStmt: " + n);
        returnStmt.getExpression().ifPresent(expression -> expression.accept(this, Var.USED));
    }

}
