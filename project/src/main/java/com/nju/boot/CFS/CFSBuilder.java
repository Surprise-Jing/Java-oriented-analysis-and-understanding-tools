package CFS;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.printer.YamlPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class CFSBuilder {
    String methodCFS;//可以换数据结构，比如一个类包含多个方法，可以换用别的数据结构进行存储

    public CFSBuilder(Path filePath) throws FileNotFoundException {
        JavaParser javeParser = new JavaParser();
        Optional<CompilationUnit> cu = javeParser.parse(new File(filePath.toString())).getResult();//CompilationUnit 树

//        1. Yaml格式输出打印
//        cu.ifPresent(YamlPrinter::print);
//        2. XML格式输出打印
//        cu.ifPresent(XmlPrinter::print);

        CFS.CFSVisitor cfsVisitor = new CFS.CFSVisitor();
        cfsVisitor.visit(cu.get(),null);
        methodCFS = cfsVisitor.getStrCFS();
    }


    public static void main(String[] args) throws FileNotFoundException{
        String absolutePath = new File("project").getAbsolutePath();
        Path filePath = Paths.get(absolutePath,"data","question_1","correct","001","BigNum.java");
        CFSBuilder cfsBuilder = new CFSBuilder(filePath);
        System.out.println(cfsBuilder.methodCFS);
    }
}
