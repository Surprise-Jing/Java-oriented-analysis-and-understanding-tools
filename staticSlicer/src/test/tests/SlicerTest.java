package tests;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.printer.SlicerPrinter;
import com.nju.boot.slicer.SlicerCriterion;
import com.nju.boot.util.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;

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
    public void Test2(){
        String fileName = Paths.get(PathUtils.PROGRAMS_FOLDER,"CFG_Test3.java").toString(),
                outFileName = Paths.get(PathUtils.PROGRAMS_OUT_FOLDER,"slicedCode","Slicer_Output3.dot").toString();
        try {
            MethodDeclaration md = getFirstMethodDeclaration(fileName);
            ACFG cfg = new ACFG();
            cfg.build(md);
            SlicerPrinter slicerPrinter = new SlicerPrinter(cfg, new FileWriter(outFileName),
                    new SlicerCriterion(new HashSet(){{add("product");}}, 13, cfg));
            slicerPrinter.print();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found");
        } catch (IOException e) {
            throw new RuntimeException("out file not found");
        }

    }}
