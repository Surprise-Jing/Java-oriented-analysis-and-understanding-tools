package com.nju.boot.slicer;

import com.github.javaparser.ast.stmt.AssertStmt;
import com.kitfox.svg.A;
import com.nju.boot.slicer.exceptions.FilePathErrorException;
import com.nju.boot.slicer.exceptions.MethodNotFoundException;
import com.nju.boot.slicer.exceptions.VariableNotFoundException;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Member;
import java.nio.file.Paths;

public class PDGSlicerTest {
    String absolutePath = new File("").getAbsolutePath();
    String srcDirPath  = Paths.get(absolutePath,"data","testcases","src").toString();
    String dstFileDirPath  = Paths.get(absolutePath,"data","testcases","output").toString();

    @Test
    void slice() {
    }

    @Test
    void getSlicedGraphNode() {
        System.out.println("x");
    }

    @Test
    void findCallableDeclaration() {
    }
    void testSlice(String directory,String fileName,int line,String variable)throws RuntimeException{
        String srcFileDirPath = Paths.get(srcDirPath,directory).toString();

        String srcFilePath = Paths.get(srcFileDirPath,fileName).toString();
        String outFileDirPath = Paths.get(dstFileDirPath,directory,fileName.substring(0,fileName.lastIndexOf('.')).toLowerCase()).toString();
        File outFileDir = new File(outFileDirPath);
        if(!outFileDir.exists())outFileDir.mkdirs();


        String outFileName = fileName+"-"+String.valueOf(line)+"-"+variable;

        String outFilePath = Paths.get(outFileDirPath,outFileName).toString();
        FileWriter outWriter = null;
        try {
            outWriter = new FileWriter(outFilePath);
            outWriter.write(new PDGSlicer(srcFilePath).slice(line,variable).getResultCode());
            outWriter.flush();
            outWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    void testWrongLine() {
        Assertions.assertThrows(MethodNotFoundException.class,()->testSlice("specific","Specific.java",2,"prod")) ;
        Assertions.assertThrows(MethodNotFoundException.class,()->testSlice("specific","Specific.java",3,"prod")) ;
    }
    @Test
    void testWrongVariable(){
        Assertions.assertThrows(VariableNotFoundException.class,()->testSlice("specific","Specific.java",5,"prod"));
    }
    @Test
    void testWrongFilePath(){
        Assertions.assertThrows(FilePathErrorException.class,()->testSlice("specific","Spseoa.java",20,"prod"));
    }
    @Test
    void getSlicedCode()  {
//        testSlice("specific","Specific.java",10,"i");
//        testSlice("specific","Specific.java",9,"sum");
//        testSlice("specific","Specific.java",7,"i");
//        testSlice("specific","Specific.java",6,"i");
//        testSlice("specific","Specific.java",6,"sum");
//        testSlice("specific","Specific.java",5,"i");
//        testSlice("specific","Specific.java",4,"sum");
//        testSlice("specific","Specific.java",3,"i");
        testSlice("specific","Specific.java",20,"prod");

    }
}
