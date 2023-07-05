import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.PrinterConfiguration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.nju.boot.graphs.callgraph.CallGraph;
import com.nju.boot.graphs.printer.CallGraphPrinter;
import com.nju.boot.graphs.printer.SelectivePrettyPrinterVisitor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Function;

public class SymbolSolverTest {
    public static final String absolutePath = new File("").getAbsolutePath();
    public static final String filePath = Paths.get(absolutePath,"data","testcases").toString();
    @Test
    public void Test() throws FileNotFoundException {
        JavaParser javaParser = new JavaParser();
        TypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver(),
                new JavaParserTypeSolver(new File(filePath)));
        JavaSymbolSolver javaSymbolSolver = new JavaSymbolSolver(typeSolver);
        javaParser.getParserConfiguration().setSymbolResolver(javaSymbolSolver);
        CompilationUnit cu =  javaParser.parse(Paths.get(filePath,"caseOne","Practicas.java").toFile()).getResult().orElse(null);
        if(cu!=null){
            System.out.println(new DefaultPrettyPrinter().print(cu));
            System.out.println();
            cu.findAll(MethodCallExpr.class).forEach(methodCallExpr -> {
                System.out.println(methodCallExpr.resolve().getQualifiedSignature());
            });
            cu.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
                System.out.println(methodDeclaration.resolve().getQualifiedSignature());
            });
            CallGraph callGraph = new CallGraph();
           // callGraph.setIncludeImportedFunctions(false);
            callGraph.build(cu);

            try {
                FileWriter fileWriter = new FileWriter(Paths.get(absolutePath,"data","output","callGraphResult.dot").toString());
                new CallGraphPrinter(callGraph,fileWriter).print();
            } catch (IOException e) {
                System.out.println("output directory doesn't exist");
                throw new RuntimeException(e);
            }


        }

    }
}
