import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.printer.Printer;
import com.nju.boot.graphs.callgraph.CallGraph;
import com.nju.boot.graphs.pdg.PDG;
import com.nju.boot.graphs.printer.CallGraphPrinter;
import com.nju.boot.graphs.printer.PDGPrinter;
import com.nju.boot.graphs.printer.SelectivePrettyPrinter;
import com.nju.boot.slicer.Graphs;
import com.nju.boot.slicer.Slicer;
import com.nju.boot.util.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.Optional;
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
        Graphs graphs = new Graphs(fileName);

        CallGraph callGraph = graphs.getCallGraph();
        System.out.println("start print call graph");
        System.out.println(callGraph);
        System.out.println("end print call graph");



        Slicer slicer = new Slicer(graphs);
        slicer.slice(20,"prod");
        System.out.println(slicer.getResult());
//        try {
//            CompilationUnit cu = getCompilationUnit(fileName);
//            MethodDeclaration md = getFirstMethodDeclaration(cu);
//            PDG pdg = new PDG();
//            pdg.build(md);
//            pdg.slice(20,"prod");
//            new PDGPrinter(pdg,new FileWriter(outFileName)).print();
//            Printer printer = new SelectivePrettyPrinter(pdg.getMarkedNodes().stream()
//                    .filter(node -> node.getAstNode()!=null)
//                    .map(node->node.getAstNode())
//                    .collect(Collectors.toSet()));
//            System.out.println(printer.print(cu));


//        } catch (FileNotFoundException e) {
//            throw new RuntimeException("file not found");
//        } catch (IOException e) {
//            throw new RuntimeException("out file not found");
//        }

    }
}
