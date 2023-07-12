package com.nju.boot.slicer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

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
    void testSlice(String directory,String fileName,int line,String variable) throws IOException {
        String srcFileDirPath = Paths.get(srcDirPath,directory).toString();

        String srcFilePath = Paths.get(srcFileDirPath,fileName).toString();
        String outFileDirPath = Paths.get(dstFileDirPath,directory,fileName.substring(0,fileName.lastIndexOf('.')).toLowerCase()).toString();
        File outFileDir = new File(outFileDirPath);
        if(!outFileDir.exists())outFileDir.mkdirs();


        String outFileName = fileName+"-"+String.valueOf(line)+"-"+variable;

        String outFilePath = Paths.get(outFileDirPath,outFileName).toString();
        FileWriter outWriter = new FileWriter(outFilePath);
        outWriter.write(new PDGSlicer(srcFilePath).getSlicedCode(line,variable));
        outWriter.flush();
        outWriter.close();
    }
    @Test
    void getSlicedCode() throws IOException {
        testSlice("specific","Specific.java",10,"i");
        testSlice("specific","Specific.java",9,"sum");
        testSlice("specific","Specific.java",7,"i");
        testSlice("specific","Specific.java",6,"i");
        testSlice("specific","Specific.java",6,"sum");
        testSlice("specific","Specific.java",5,"i");
        testSlice("specific","Specific.java",4,"sum");
        testSlice("specific","Specific.java",3,"i");

    }
}
