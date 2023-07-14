package com.nju.boot.slicer;

import com.github.javaparser.ast.CompilationUnit;
import com.nju.boot.graphs.Graphs;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.callgraph.CallGraph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.dependencegraph.DominatorTree;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.graphs.printer.*;
import com.nju.boot.metrics.CodeMetrics;
import com.nju.boot.slicer.exceptions.FileUnparsableException;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

class GraphsTest {
    public static String absolutePath = new File("").getAbsolutePath();
    public static String relativePath = Paths.get("data", "testcases").toString();


    /**
     * 测试指定目录下的所有java文件并输出结果
     */
    public void testFiles(String directory) throws IOException {
        Path srcDirectoryPath = Paths.get(absolutePath, relativePath, "src", directory);
        File srcDirectory = srcDirectoryPath.toFile();
        if (!srcDirectory.exists()) {
            throw new FileNotFoundException("源文件目录不存在");
        } else if (!srcDirectory.isDirectory()) {
            throw new RuntimeException("指定的路径非文件夹");
        }
        File[] srcFiles = srcDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory() || !pathname.toString().endsWith(".java"))
                    return false;
                return true;
            }
        });
        for (File srcFile : srcFiles) {
            Graphs graphs = new Graphs(srcFile);


            String fileNameWithoutExtension = srcFile.getName().substring(0, srcFile.getName().lastIndexOf('.'));
            Path outputDirPath = Paths.get(absolutePath, relativePath, "output", directory, fileNameWithoutExtension);
            File outputDir = outputDirPath.toFile();
            if (!outputDir.exists()) outputDir.mkdirs();

            //导出callGraph的结果
            CallGraph callGraph = graphs.getCallGraph();
            File dotFile = Paths.get(outputDirPath.toString(), "cg.dot").toFile();
            File pngFile = Paths.get(outputDirPath.toString(), "cg.png").toFile();
            new CallGraphPrinter(callGraph,
                    new FileWriter(dotFile))
                    .print();
            Graphviz.fromFile(dotFile).render(Format.PNG).toFile(pngFile);

            //导出cfg和pdg的结果
            Set<String> mSigs = graphs.getQualifiedSignatures();
            for (String mSig : mSigs) {
                String plainSig = mSig.substring(mSig.lastIndexOf('.') + 1);
                CFG cfg = graphs.getCFG(mSig);
                PDG pdg = graphs.getPDG(mSig);
                ACFG acfg = pdg.getAcfg();

                String cfgDirPath = Paths.get(outputDirPath.toString(), "cfg").toString();
                Path cfgDotDirPath = Paths.get(cfgDirPath, "dot");
                Path cfgPngDirPath = Paths.get(cfgDirPath, "png");

                if (!cfgDotDirPath.toFile().exists()) cfgDotDirPath.toFile().mkdirs();
                if (!cfgPngDirPath.toFile().exists()) cfgPngDirPath.toFile().mkdirs();


                Path cfgDotPath = Paths.get(cfgDotDirPath.toString(), plainSig + ".dot");

                Path cfgPngPath = Paths.get(cfgPngDirPath.toString(), plainSig + ".png");
                FileWriter cfgWriter = new FileWriter(cfgDotPath.toFile());
                //导出cfg

                new CFGPrinter(cfg, cfgWriter).print();
                Graphviz.fromFile(cfgDotPath.toFile()).render(Format.PNG).toFile(cfgPngPath.toFile());


                String pdgDirPath = Paths.get(outputDirPath.toString(), "pdg").toString();
                Path pdgDotDirPath = Paths.get(pdgDirPath, "dot");
                Path pdgPngDirPath = Paths.get(pdgDirPath, "png");

                if (!pdgDotDirPath.toFile().exists()) pdgDotDirPath.toFile().mkdirs();
                if (!pdgPngDirPath.toFile().exists()) pdgPngDirPath.toFile().mkdirs();

                //导出pdg
                Path pdgDotPath = Paths.get(pdgDotDirPath.toString(), plainSig + ".dot");
                Path pdgPngPath = Paths.get(pdgPngDirPath.toString(), plainSig + ".png");
                FileWriter pdgWriter = new FileWriter(pdgDotPath.toFile());


                new PDGPrinter(pdg, pdgWriter).print();
                Graphviz.fromFile(pdgDotPath.toFile()).render(Format.PNG).toFile(pdgPngPath.toFile());



            }
   }
    }


    @Test
    public void ifTests() throws IOException {
        testFiles("if");
    }

    @Test
    public void sequenceTests() throws IOException {
        testFiles("sequence");
    }
    @Test
    public void whileTests() throws IOException {
        testFiles("while");
    }
    @Test
    public void switchTests()throws  IOException{
        testFiles("switch");
    }
    //@Test
    public void testSpecific() throws IOException{
        testFiles("specific");
    }
    @Test
    public void testWrongFile(){
        Assertions.assertThrows(FileUnparsableException.class,()->testFiles("error"));
    }
}
