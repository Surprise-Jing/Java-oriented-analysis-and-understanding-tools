package com.nju.boot.exec;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.Graph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.util.PathUtils;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Run {

    public static String PROGRAM = PathUtils.PROGRAMS_FOLDER + "\\CFG_Test4.java";

    public static String OUTFILE = PathUtils.PROGRAMS_OUT_FOLDER + "\\CFG_Test4.dot";


    public static void main(String[] args) {
        File file = new File(PROGRAM);
        try{
            CFG cfg = buildCFG(file);
            printCFG(cfg);
        }
        catch (FileNotFoundException e){
            System.out.println("file not found!");
        }
        catch (IOException e){
            System.out.println("outfile not found!");
        }

    }

    public static CFG buildCFG(File file) throws FileNotFoundException {
        JavaParser javaParser = new JavaParser();
        Optional<CompilationUnit> cu = javaParser.parse(file).getResult();
        Optional<MethodDeclaration> optionalMethodDeclaration = cu.get().findFirst(MethodDeclaration.class);
        if(!optionalMethodDeclaration.isPresent()){
            throw new RuntimeException("No method could be found");
        }
        CFG cfg = new CFG();
        cfg.build(optionalMethodDeclaration.get());
        return cfg;
    }

    public static void printCFG(CFG cfg) throws IOException {
        FileWriter fileWriter = new FileWriter(OUTFILE);
        DOTExporter<GraphNode<?>, Edge> dotExporter = new DOTExporter<>(v->String.valueOf(v.getId()));
        dotExporter.setVertexAttributeProvider(
                v->{
                    Map<String, Attribute> map = new HashMap<>();
                    map.put("label", DefaultAttribute.createAttribute(v.getInstruction()));
                    return map;
                }
        );
        dotExporter.exportGraph(cfg, fileWriter);
    }
}
