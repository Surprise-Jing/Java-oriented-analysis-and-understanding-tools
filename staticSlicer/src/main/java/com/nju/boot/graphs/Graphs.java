package com.nju.boot.graphs;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.nju.boot.graphs.augmented.ACFG;
import com.nju.boot.graphs.callgraph.CallGraph;
import com.nju.boot.graphs.cfg.CFG;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.exceptions.FilePathErrorException;
import com.nju.boot.slicer.exceptions.FileUnparsableException;
import com.nju.boot.util.GraphsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Graphs {
    File file = null;
    CompilationUnit cu;
    Map<CallableDeclaration<?>,CFG> cfgMap= new HashMap<>();
    CallGraph callGraph;
    Map<CallableDeclaration<?>, PDG> pdgMap = new HashMap<>();
    Map<CallableDeclaration<?>, CDG> cdgMap = new HashMap<>();
    boolean parsed = true;

    public Graphs(String filePath) {
        this(new File(filePath));
    }
    public CompilationUnit getCu(){return cu;}
    public Graphs(File fileToSlice) {
        this.file = fileToSlice;
        generateGraphsFromFile();

    }
    public void generateGraphsFromFile(){
        try{
            getCompilationUnit();
            if (parsed ){
                generateCFGandPDG();
                generateCG();
            }
        }
        catch (RuntimeException e){
            throw new FileUnparsableException();
        }



    }
    public String getFirstClassName(){
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = null;
        for(TypeDeclaration<?> t:cu.getTypes()){
            if (t.isClassOrInterfaceDeclaration()){
                if(!((ClassOrInterfaceDeclaration)t).isInterface())
                {
                    classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) t;
                    break;
                }
            }
        }
        if(classOrInterfaceDeclaration==null)
            return "";
        else
            return classOrInterfaceDeclaration.getNameAsString();
    }
    private void getCompilationUnit(){
        JavaParser javaParser = new JavaParser();
        TypeSolver typeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        javaParser.getParserConfiguration().setSymbolResolver(symbolSolver);

        try {
            Optional<CompilationUnit> _cu =  javaParser.parse(file).getResult();
            cu = _cu.orElse(null);

            if (cu==null||cu.getParsed()== Node.Parsedness.UNPARSABLE) {
                cu = null;
                parsed = false;
                throw new FileUnparsableException();
            }
        }catch (FileNotFoundException e){
            throw new FilePathErrorException();
        }
    }
    private void generateCFGandPDG(){
        for(TypeDeclaration<?> typeDeclaration: cu.getTypes()){
            for(BodyDeclaration<?> bodyDeclaration : typeDeclaration.getMembers()){
                if(bodyDeclaration instanceof CallableDeclaration<?>){
                    CFG newCFG = new CFG();
                    newCFG.build((CallableDeclaration<?>) bodyDeclaration);
                    cfgMap.put((CallableDeclaration<?>) bodyDeclaration,newCFG);
                    ACFG newACFG  = new ACFG();
                    newACFG.build((CallableDeclaration<?>) bodyDeclaration);

                    PDG pdg = new PDG();
                    pdg.buildFromACFG(newACFG);
                    pdgMap.put((CallableDeclaration<?>) bodyDeclaration,pdg);

                    CDG cdg = new CDG();
                    cdg.buildFromACFG(newACFG);
                    cdgMap.put((CallableDeclaration<?>) bodyDeclaration,cdg);
                }
            }

        }
    }
    private void generateCG(){
        CallGraph cg = new CallGraph();
        cg.build(cu);
        this.callGraph = cg;
    }
    public CFG getCFG(String methodSignature){
        return getCFG(GraphsUtil.findMethodBySignature(this,methodSignature));
    }
    public CFG getCFG(CallableDeclaration<?> callableDeclaration){
        return cfgMap.get(callableDeclaration);
    }
    public PDG getPDG(CallableDeclaration<?> callableDeclaration){
        return pdgMap.get(callableDeclaration);
    }
    public PDG getPDG(String methodSignature){
        return pdgMap.get(GraphsUtil.findMethodBySignature(this,methodSignature));
    }
    public CDG getCDG(CallableDeclaration<?> callableDeclaration){return cdgMap.get(callableDeclaration);}
    public CDG getCDG(String methodSignature)
    {return cdgMap.get(GraphsUtil.findMethodBySignature(this,methodSignature));}
    public CallGraph getCallGraph(){
        return this.callGraph;
    }
    public Set<String> getQualifiedSignatures(){
        Map<String, GraphNode<CallableDeclaration<?>>> sigToNodeMap = this.callGraph.getSignatureToNodeMap();
        return sigToNodeMap.keySet().stream().filter(sig->
            sigToNodeMap.get(sig).getAstNode()!=null
        ).collect(Collectors.toSet());
    }



}
