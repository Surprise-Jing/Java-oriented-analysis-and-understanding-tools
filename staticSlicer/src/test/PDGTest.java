import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.Printer;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.PrinterConfiguration;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.pdg.PDG;
import com.nju.boot.graphs.printer.PDGPrinter;
import com.nju.boot.graphs.printer.SelectivePrettyPrinter;
import com.nju.boot.graphs.printer.SelectivePrettyPrinterVisitor;
import com.nju.boot.slicer.PDGMarker;
import com.nju.boot.util.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PDGTest {
    public CompilationUnit getCompilationUnit(String fileName) throws FileNotFoundException {
        JavaParser javaParser = new JavaParser();
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
    public void testSix(){
        String fileName = Paths.get(PathUtils.PROGRAMS_FOLDER,"CFG_Test6.java").toString(),
                outFileName = Paths.get(PathUtils.PROGRAMS_OUT_FOLDER,"graph","PDG_Output6.dot").toString();
        try {
            CompilationUnit cu = getCompilationUnit(fileName);
            MethodDeclaration md = getFirstMethodDeclaration(cu);
            PDG pdg = new PDG();
            pdg.build(md);
            pdg.slice(17,"prod");
            new PDGPrinter(pdg,new FileWriter(outFileName)).print();
            Printer printer = new SelectivePrettyPrinter(pdg.getMarkedNodes().stream()
                    .filter(node -> node.getAstNode()!=null)
                    .map(node->node.getAstNode())
                    .collect(Collectors.toSet()));
            System.out.println(printer.print(cu));

        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found");
        } catch (IOException e) {
            throw new RuntimeException("out file not found");
        }

    }
}
