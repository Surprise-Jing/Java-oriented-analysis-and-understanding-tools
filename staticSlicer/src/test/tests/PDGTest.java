package tests;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.printer.Printer;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.callgraph.CallGraph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.pdg.PDG;
import com.nju.boot.graphs.printer.CFGPrinter;
import com.nju.boot.graphs.printer.CallGraphPrinter;
import com.nju.boot.graphs.printer.PDGPrinter;
import com.nju.boot.graphs.printer.SelectivePrettyPrinter;
import com.nju.boot.slicer.Graphs;
import com.nju.boot.slicer.Slicer;
import com.nju.boot.util.PDGMarker;
import com.nju.boot.util.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

       // Graphs graphs = new Graphs(fileName);
       // Set<String>methods = graphs.getQualifiedSignatures();
        //for(String method:methods){
        //    System.out.println("the 圈复杂度 of "+method+" is "+ String.valueOf(graphs.getCyclomaticComplexity(method)));
       // }

//        try {
//            CompilationUnit cu = getCompilationUnit(fileName);
//            MethodDeclaration md = getFirstMethodDeclaration(cu);
//            PDG pdg = new PDG();
//            ACFG cfg = new ACFG();
//            pdg.build(md);
//            cfg.build(md);
//            PDGMarker pdgMarker = new PDGMarker(pdg);
//            pdgMarker.mark(18,"a");
           // new PDGPrinter(pdg,new FileWriter(outFileName)).print();
           // new CFGPrinter(cfg,new FileWriter(cfgOut)).print();

//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException("file not found");
//        } catch (IOException e) {
//            throw new RuntimeException("out file not found");
//        }

    }
}
