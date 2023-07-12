package tests;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.slicer.printer.SelectivePrettyPrinter;

import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.AbstractSlicer;
import com.nju.boot.slicer.DataFlowEquationSlicer;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.util.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SlicerTest {


    public MethodDeclaration getFirstMethodDeclaration(String fileName) throws FileNotFoundException {
        System.out.println(1);
        JavaParser javaParser = new JavaParser();
        File file = new File(fileName);
        Optional<CompilationUnit> cu = javaParser.parse(file).getResult();

        Optional<MethodDeclaration> optionalMethodDeclaration = cu.get().findFirst(MethodDeclaration.class);
        if(!optionalMethodDeclaration.isPresent()){
            throw new RuntimeException("No method could be found");
        }
        return optionalMethodDeclaration.get();
    }

    @Test
    public void Test2() throws IOException {
        String fileName = Paths.get(PathUtils.PROGRAMS_FOLDER,"CFG_Test3.java").toString(),
                outDirPath = Paths.get(PathUtils.PROGRAMS_OUT_FOLDER,"slicedCode").toString(),
                outFileName = "Slicer_Output3.java";
//        try {
            //MethodDeclaration md = getFirstMethodDeclaration(fileName);
            //ACFG cfg = new ACFG();
            //cfg.build(md);
           // SlicerPrinter slicerPrinter = new SlicerPrinter(fileName, new FileWriter(outFileName),
               //     new SlicerCriterion(new HashSet(){{add("product");}}, 13, cfg));
            //slicerPrinter.print();

//        } catch (FileNotFoundException e) {
//            throw new RuntimeException("file not found");
//        } catch (IOException e) {
//            throw new RuntimeException("out file not found");
//        }
        Graphs graphs = new Graphs(fileName);
        AbstractSlicer slicer = new DataFlowEquationSlicer(graphs);
        Set<GraphNode<?>> slicedResults = slicer.slice(13,"product");
        File outDir = new File(outDirPath);
        if(!outDir.exists())outDir.mkdirs();
        FileWriter writer = new FileWriter(Paths.get(outDirPath,outFileName).toFile());
        String res = new SelectivePrettyPrinter(slicedResults.stream().map(n->n.getAstNode()).collect(Collectors.toSet())).print(graphs.getCu());
        writer.write(res);
        writer.flush();

    }}
