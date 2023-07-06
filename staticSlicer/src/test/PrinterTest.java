import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.nju.boot.util.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class PrinterTest {
    @Test
    public void testDefaultPrinter() throws IOException {
        JavaParser javaParser = new JavaParser();
        String fileName = Paths.get(PathUtils.PROGRAMS_FOLDER,"CFG_Test6.java").toString();
        String outFileName = Paths.get(PathUtils.PROGRAMS_OUT_FOLDER,"slicedCode","outPutCode_6.java").toString();
        CompilationUnit cu = javaParser.parse(new File(fileName)).getResult().get();
        DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter();
        String result = defaultPrettyPrinter.print(cu.getType(0).getMember(0));
        FileWriter fileWriter = new FileWriter(outFileName);
        fileWriter.write(result);
        fileWriter.flush();

    }
}