import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.pdg.PDG;
import com.nju.boot.graphs.printer.PDGPrinter;
import com.nju.boot.util.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

public class PDGTest {
    public MethodDeclaration getFirstMethodDeclaration(String fileName) throws FileNotFoundException {
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
    public void testSix(){
        String fileName = Paths.get(PathUtils.PROGRAMS_FOLDER,"CFG_Test6.java").toString(),
                outFileName = Paths.get(PathUtils.PROGRAMS_OUT_FOLDER,"PDG_Output6.dot").toString();
        try {
            MethodDeclaration md = getFirstMethodDeclaration(fileName);
            PDG pdg = new PDG();
            pdg.build(md);
            new PDGPrinter(pdg,new FileWriter(outFileName)).print();

        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found");
        } catch (IOException e) {
            throw new RuntimeException("out file not found");
        }

    }
}
