package tests;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.nju.boot.util.PathUtils;
import com.nju.boot.util.GraphUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PrinterTest {
    @Test
    public void testDefaultPrinter() throws IOException {
        JavaParser javaParser = new JavaParser();
        String fileName = Paths.get(PathUtils.PROGRAMS_FOLDER,"CFG_Test6.java").toString();
        String outFileName = Paths.get(PathUtils.PROGRAMS_OUT_FOLDER,"slicedCode","outPutCode_6.java").toString();
        CompilationUnit cu = javaParser.parse(new File(fileName)).getResult().get();
        System.out.println(GraphUtil.astNodeToXml(cu));

    }
}
