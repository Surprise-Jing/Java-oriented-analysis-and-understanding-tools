package tests;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.nju.boot.metrics.CodeMetrics;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.slicer.PDGSlicer;
import com.nju.boot.util.PathUtils;
import com.nju.boot.util.GraphsUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

public class PDGTest {
    public CompilationUnit getCompilationUnit(String fileName) throws FileNotFoundException {
        JavaParser javaParser = new JavaParser();
        javaParser.getParserConfiguration().setSymbolResolver(new JavaSymbolSolver(new ReflectionTypeSolver()));
        File file = new File(fileName);
        CompilationUnit cu = javaParser.parse(file).getResult().orElse(null);
        return cu;
    }
    public MethodDeclaration getFirstMethodDeclaration(CompilationUnit cu){
        Optional<MethodDeclaration> optionalMethodDeclaration = cu.findFirst(MethodDeclaration.class);
        if(!optionalMethodDeclaration.isPresent()){
            throw new RuntimeException("No method could be found");
        }
        return optionalMethodDeclaration.get();
    }
    public MethodDeclaration getFirstMethodDeclaration(String fileName) throws FileNotFoundException {
        CompilationUnit cu = getCompilationUnit(fileName);
        if(cu == null)return null;
        else return getFirstMethodDeclaration(cu);

    }

    @Test
    public void testSix() throws FileNotFoundException {
        String fileName = Paths.get(PathUtils.PROGRAMS_FOLDER,"CFG_Test2.java").toString(),
                outFileName = Paths.get(PathUtils.PROGRAMS_OUT_FOLDER,"graph","PDG_Output2.dot").toString(),
                cfgOut = Paths.get(PathUtils.PROGRAMS_OUT_FOLDER,"graph","ACFG_Output2.dot").toString();
        CompilationUnit cu = getCompilationUnit(fileName);
       Graphs graphs = new Graphs(fileName);
       System.out.println(graphs.getFirstClassName());
       Set<String>methods = graphs.getQualifiedSignatures();
        CallableDeclaration<?>callableDeclaration = GraphsUtil.findMethodByLineNumber(graphs.getCu(),29);
        String signature  = null;
        if(callableDeclaration instanceof MethodDeclaration){
            signature = ((MethodDeclaration) callableDeclaration).resolve().getQualifiedSignature();
        }
        else if(callableDeclaration instanceof ConstructorDeclaration){
            signature = ((ConstructorDeclaration) callableDeclaration).resolve().getQualifiedSignature();
        }

        CodeMetrics codeMetrics = new CodeMetrics(graphs);
        for(String method:methods){
           System.out.println("the 圈复杂度 of "+method+" is "+ String.valueOf(codeMetrics.getCyclomaticComplexity(method)));
           System.out.println("the LOC of "+method+" is "+ String.valueOf(codeMetrics.getLinesCodeOfMethod(method)));
           System.out.println("调用函数数量 "+method+" is "+ String.valueOf(codeMetrics.getTimesCalling(method)));
           System.out.println("被调用的次数 "+method+" is "+ String.valueOf(codeMetrics.getTimesCalled(method)));
           System.out.println("入参个数 "+method+" is "+ String.valueOf(codeMetrics.getNumOfParameters(method)));
           if(signature!=null&&signature.equals(method))
        System.out.println("slice on 23:a is:\n"+new PDGSlicer(graphs).getSlicedCode(29,"a"));}
}

//            MethodDeclaration md = getFirstMethodDeclaration(c

}
