package com.nju.boot.metrics;

import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

//todo:完善
/**count lines of code, ignoring comments and blank lines*/
public class LineCounterVisitor extends VoidVisitorAdapter<Void> {
    public int getCount() {
        return count;
    }

    private int count = 0;

    @Override
    public void visit(BreakStmt n, Void arg) {
        count++;
        super.visit(n, arg);
    }

    @Override
    public void visit(ContinueStmt n, Void arg) {
        count++;super.visit(n, arg);
    }

    @Override
    public void visit(DoStmt n, Void arg) {
        count++;super.visit(n, arg);
    }

    @Override
    public void visit(ExpressionStmt n, Void arg) {
        count++;super.visit(n, arg);
    }

    @Override
    public void visit(ForEachStmt n, Void arg) {
        count++;super.visit(n, arg);
    }

    @Override
    public void visit(ForStmt n, Void arg) {
        count++;
        super.visit(n, arg);
    }

    @Override
    public void visit(IfStmt n, Void arg) {
        count++;
        super.visit(n, arg);
    }

    @Override
    public void visit(LabeledStmt n, Void arg) {
        count++;
        super.visit(n, arg);
    }

    @Override
    public void visit(ReturnStmt n, Void arg) {
        count++;
        super.visit(n, arg);
    }

    @Override
    public void visit(SwitchStmt n, Void arg) {
        count++;
        super.visit(n, arg);
    }

    @Override
    public void visit(SwitchEntry n, Void arg) {
        count++;
        super.visit(n, arg);
    }



    @Override
    public void visit(WhileStmt n, Void arg) {
        count++;
        super.visit(n, arg);
    }
}
