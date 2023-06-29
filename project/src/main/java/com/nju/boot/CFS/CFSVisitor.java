package CFS;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;

public class CFSVisitor extends VoidVisitorAdapter {
    private String strCFS="";//可以换数据结构，比如一个类包含多个方法，可以换别的数据结构来存储
    private final ArrayList<String> cfsNode;

    public CFSVisitor(){
        cfsNode = new ArrayList<>();
    }

    private void addCFSNode(String node){
        if(cfsNode.size()!=0){
            strCFS += ",";
        }
        strCFS += node;
        cfsNode.add(node);
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        strCFS+=n.getName()+"\n";
        super.visit(n, arg);
    }

    @Override
    public void visit(IfStmt n, Object arg){
        //TODO:Perfect this method
        addCFSNode("If_start");
        visit(n.getThenStmt(),arg);
        addCFSNode("If_end");

        if(n.hasElseBlock()||n.hasElseBranch()){
            if(n.getElseStmt().isPresent()){
                addCFSNode("Else_start");
                visit(n.getElseStmt().get(),arg);
                addCFSNode("Else_end");
            }
        }
    }
    @Override
    public void visit(ForStmt n, Object arg){
        //TODO:Perfect this method
        addCFSNode("For_start");
        visit(n.getBody(),arg);
        addCFSNode("For_end");
    }

    public void visit(Statement statement,Object arg){
        if(statement instanceof  BlockStmt){
            super.visit((BlockStmt) statement,arg);
        }else if(statement instanceof IfStmt){
            visit((IfStmt) statement,arg);
        }else if(statement instanceof SwitchStmt){
            super.visit((SwitchStmt) statement,arg);
        }else if(statement instanceof ForStmt){
            visit((ForStmt) statement,arg);
        }else if(statement instanceof WhileStmt){
            super.visit((WhileStmt) statement,arg);
        }else if(statement instanceof ContinueStmt){
            super.visit((ContinueStmt) statement,arg);
        }else if(statement instanceof BreakStmt){
            super.visit((BreakStmt) statement,arg);
        }else if(statement instanceof ReturnStmt){
            super.visit((ReturnStmt) statement,arg);
        }
    }
    public String getStrCFS(){
        return strCFS;
    }
}
