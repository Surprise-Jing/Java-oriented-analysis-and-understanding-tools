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
    void testSlice(String directory,String fileName,int line,String variable){
        testSlice(directory,fileName,line,variable,true);
    }
    void testSlice(String directory,String fileName,int line,String variable,boolean isPDGSlicer)throws RuntimeException{
        String srcFileDirPath = Paths.get(srcDirPath,directory).toString();

        String srcFilePath = Paths.get(srcFileDirPath,fileName).toString();
        String outFileDirPath = Paths.get(dstFileDirPath,directory,fileName.substring(0,fileName.lastIndexOf('.')).toLowerCase()).toString();
        File outFileDir = new File(outFileDirPath);
        if(!outFileDir.exists())outFileDir.mkdirs();


        String outFileName = fileName+"-"+String.valueOf(line)+"-"+variable;
        outFileName += '-' + ((isPDGSlicer)?"PDG":"DataFlow");

        String outFilePath = Paths.get(outFileDirPath,outFileName).toString();
        FileWriter outWriter = null;
        try {
            outWriter = new FileWriter(outFilePath);
            AbstractSlicer abstractSlicer = null;
            if(isPDGSlicer){
                abstractSlicer = new PDGSlicer(srcFilePath);
            }
            else
                abstractSlicer = new DataFlowEquationSlicer(srcFilePath);
            outWriter.write(abstractSlicer.slice(line,variable).getResultCode());
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
        //输入错误的行号
        Assertions.assertThrows(MethodNotFoundException.class,()->testSlice("specific","Specific.java",33,"prod")) ;
        Assertions.assertThrows(MethodNotFoundException.class,()->testSlice("specific","Specific.java",1,"prod")) ;
    }
    @Test
    void testWrongVariable(){
        //输入不存在的变量
        Assertions.assertThrows(VariableNotFoundException.class,()->testSlice("specific","Specific.java",10,"b"));
    }
    @Test
    void testWrongFilePath(){
        //输入错误的文件路径
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

        testSlice("simpleprograms","Prog01.java",10,"sum_q");
        testSlice("simpleprograms","Prog01.java",10,"sum_q",false);
        testSlice("simpleprograms","Prog01.java",11,"product_q");
        testSlice("simpleprograms","Prog01.java",11,"product_q",false);
        testSlice("simpleprograms","Prog01.java",11,"product_q");
        testSlice("simpleprograms","Prog01.java",11,"product_q",false);
        testSlice("simpleprograms","Prog02.java",9,"a");
        testSlice("simpleprograms","Prog02.java",9,"a",false);
        testSlice("simpleprograms","Prog02.java",7,"a");
        testSlice("simpleprograms","Prog02.java",7,"a",false);
        testSlice("simpleprograms","Prog02.java",12,"b");//可体现break等语句的切片
        testSlice("simpleprograms","Prog02.java",12,"b",false);
        testSlice("simpleprograms","Prog3.java",13,"a");
        testSlice("simpleprograms","Prog3.java",13,"a",false);
        testSlice("simpleprograms","Prog3.java",11,"a",false);


    }
}
