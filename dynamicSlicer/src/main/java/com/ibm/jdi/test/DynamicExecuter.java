package com.ibm.jdi.test;

import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.graphs.dependencegraph.PDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.SlicerCriterion;
import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.StepRequest;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

@Data
public class DynamicExecuter {
    private String debugClass;

//    private int[] breakPointLines;

    private List<Integer> logOfLines;

    private PDG ddg;

    private Map<String, DynamicNode> DefnNode = new HashMap<>();

    private Map<Integer, DynamicNode> PrednNode = new HashMap<>();

    private Map<Integer, List<DynamicNode>> CurrentNode = new HashMap<>();

    private List<Set<Integer>> ReachableStmt = new ArrayList<Set<Integer>>();

    private void setLogOfLines(){
        logOfLines = new ArrayList<Integer>();
    }

    public VirtualMachine connectAndLaunchVM(String path, String fileName) throws Exception {
        ProcessBuilder compileDebugee = new ProcessBuilder("javac","-g", fileName);
        compileDebugee.directory(new File(path));
        Process process = compileDebugee.start();
        process.waitFor();
        LaunchingConnector launchingConnector = Bootstrap.virtualMachineManager()
                .defaultConnector();
        Map<String, Connector.Argument> arguments = launchingConnector.defaultArguments();
        arguments.get("main").setValue(debugClass);
        arguments.get("options").setValue("-cp " + path);
        return launchingConnector.launch(arguments);
    }

    public void executeFile(String path, String fileName, String className, String input) throws Exception {
        this.setDebugClass(className);
        this.setLogOfLines();
        VirtualMachine vm = null;
        try {
            vm = this.connectAndLaunchVM(path, fileName);

            Process process = vm.process();
            OutputStream outputStream = process.getOutputStream();
//            String str = "12";
            outputStream.write(input.getBytes());
            outputStream.flush();
            outputStream.close();

            this.enableClassPrepareRequest(vm);

            EventSet eventSet = null;
            while ((eventSet = vm.eventQueue().remove()) != null) {
                for (Event event : eventSet) {
                    if (event instanceof VMStartEvent) {
                        System.out.println("VM started");
                    }
                    if (event instanceof ClassPrepareEvent) {
                        this.setBreakPoints(vm, (ClassPrepareEvent)event);
                    }
                    if (event instanceof BreakpointEvent) {
                        this.enableStepRequest(vm, (BreakpointEvent)event);
                    }
                    if (event instanceof StepEvent) {
                        this.updatingLog((StepEvent) event);
                    }
                    vm.resume();
                }
            }
        } catch (VMDisconnectedException e) {
            System.out.println("Virtual Machine is disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            InputStreamReader reader = new InputStreamReader(vm.process().getInputStream());
            OutputStreamWriter writer = new OutputStreamWriter(System.out);
            char[] buf = new char[512];
            reader.read(buf);
            writer.write(buf);
            writer.flush();
        }

        vm.process().destroy();
    }

    public void enableClassPrepareRequest(@NotNull VirtualMachine vm) {
        ClassPrepareRequest classPrepareRequest = vm.eventRequestManager().createClassPrepareRequest();
        classPrepareRequest.addClassFilter(debugClass);
        classPrepareRequest.enable();
    }

    public void setBreakPoints(@NotNull VirtualMachine vm, @NotNull ClassPrepareEvent event) throws AbsentInformationException, NoSuchMethodException {
        ClassType classType = (ClassType) event.referenceType();
        Method methods = classType.methodsByName("main").get(0);
        List<Location> locations = methods.allLineLocations();
        Location mainEntryLocation = locations.get(0);
        BreakpointRequest bpReq = vm.eventRequestManager().createBreakpointRequest(mainEntryLocation);
        bpReq.enable();
    }

    public void updatingLog(@NotNull LocatableEvent event) throws IncompatibleThreadStateException,
            AbsentInformationException {
        StackFrame stackFrame = event.thread().frame(0);
        if(stackFrame.location().toString().contains(debugClass)) {
            Map<LocalVariable, Value> visibleVariables = stackFrame
                    .getValues(stackFrame.visibleVariables());
            System.out.println("Variables at " + stackFrame.location().toString() +  " > ");
            logOfLines.add(stackFrame.location().lineNumber());
        }
    }
    public void enableStepRequest(@NotNull VirtualMachine vm, @NotNull BreakpointEvent event) throws IncompatibleThreadStateException {
        // first line log
        StackFrame stackFrame = event.thread().frame(0);
        System.out.println("Variables at " + stackFrame.location().toString() +  " > ");
        logOfLines.add(stackFrame.location().lineNumber());

        StepRequest stepRequest = vm.eventRequestManager()
                .createStepRequest(event.thread(), StepRequest.STEP_LINE, StepRequest.STEP_OVER);
        stepRequest.enable();
    }

    /*    public void InitiateDDG(int size){
            //initiate ReachableStmt

            for (int i = 0; i < size; i++) {
                *//* ReachableStmt[i] = {} *//*
     *//* 遍历 静态依赖图 *//*
            GraphNode<?> GN = cfg.getRootNode().get(); // 行号i to 结点GN
//            Class<GraphNode<?>> graphNodeClass = null;
            Set<Integer> nl = new HashSet<>();
//            bfs 遍历结点GN
            List<Node> childs = GN.getAstNode().getChildNodes();
            LinkedList<Node> queue = new LinkedList<>(childs);

            while(!queue.isEmpty()){
                Node n = queue.peek();
                queue.remove();
                queue.addAll(n.getChildNodes()); //未去重
                nl.add(0); //未填入具体id
            }

            ReachableStmt.add(nl);
        }
    }*/
    public void buildingDDG(CDG _cdg) throws NoSuchElementException {
        for(int i : logOfLines){
//                        DDG
//            ReachableStmt /staic /const
            SlicerCriterion slicerCriterion = new SlicerCriterion(null, i, _cdg);
            Set<GraphNode<?>> graphNodes = slicerCriterion.findNodeByLineNumber(_cdg);

            for(GraphNode<?> GN : graphNodes /* 行号i to 结点GN */){
                Set<DynamicNode> Def = new HashSet<>();
                Set<DynamicNode> Pred = new HashSet<>();
                Set<Integer> Reach = new HashSet<>();
                Reach.add(i);

                Set<String> Use = GN.getUsedVariables();
                for(String v : Use){
//                    System.out.println("Used Variable: " + v);
                    Def.add(DefnNode.get(v));
//                    System.out.println("add reachable: " + DefnNode.get(v).getReachableStmt());
                    Reach.addAll(DefnNode.get(v).getReachableStmt());
                }

                for(Edge inEdge : _cdg.incomingEdgesOf(GN)){
                    GraphNode<?> p = _cdg.getEdgeSource(inEdge);
                    Integer NodeId = p.getAstNode().getBegin().get().line;
//                        System.out.println("Node ID: " + NodeId);
                    if(PrednNode.containsKey(NodeId)){
                        Pred.add(PrednNode.get(NodeId));
//                        System.out.println("add reachable: " + PrednNode.get(NodeId).getReachableStmt());
                        Reach.addAll(PrednNode.get(NodeId).getReachableStmt());
                    }
                }

                if(GN.getAstNode() instanceof BreakStmt){
                    for(Edge outEdge : _cdg.outgoingEdgesOf(GN)){
                        GraphNode<?> w = _cdg.getEdgeTarget(outEdge);
                        Integer LoopId = w.getAstNode().getBegin().get().line;
                        CurrentNode.get(LoopId).get(CurrentNode.get(LoopId).size() - 1).getReachableStmt().add(i);
                    }
                }

                if(GN.getAstNode() instanceof ContinueStmt){
                    for(Edge outEdge : _cdg.outgoingEdgesOf(GN)){
                        GraphNode<?> w = _cdg.getEdgeTarget(outEdge);
                        Integer LoopId = w.getAstNode().getBegin().get().line;
                        CurrentNode.get(LoopId).get(CurrentNode.get(LoopId).size() - 1).getReachableStmt().add(i);
                    }
                }

                /*Set<Edge> edges = new HashSet<>(_cdg.incomingEdgesOf(GN));
                Set<GraphNode<?>> visited = new HashSet<>();
                visited.add(GN);

                while(!edges.isEmpty()){
                    Set<Edge> visitEdge = new HashSet<>(edges);
                    edges.clear();
                    for(Edge inEdge:visitEdge){
                        GraphNode<?> p = _cdg.getEdgeSource(inEdge);
                        Integer NodeId = p.getAstNode().getBegin().get().line;
//                        System.out.println("Node ID: " + NodeId);

                        if(PrednNode.containsKey(NodeId)){
                            Pred.add(PrednNode.get(NodeId));
                            Reach.addAll(PrednNode.get(NodeId).getReachableStmt());
                        }

                        for(Edge nextEdge: _cdg.incomingEdgesOf(p)){
                            GraphNode<?> nextP = _cdg.getEdgeSource(nextEdge);
                            if(!visited.contains(nextP)){
                                edges.add(nextEdge);
                                visited.add(nextP);
                            }
                        }
                    }
                }*/

//                System.out.println("Line " + i + ":" + Reach);

                if( CurrentNode.containsKey(i)/*S[i] was Executed*/ && CurrentNode.get(i).get(CurrentNode.get(i).size() - 1).sameDef(Def) && CurrentNode.get(i).get(CurrentNode.get(i).size() - 1).samePred(Pred) ){
//                still Node n
                }
                else {
//                new Node n'
                    DynamicNode newDn = new DynamicNode(i, Def, Pred, Reach);
//                Dealing Loop Control
                    List<DynamicNode> childNodes = new ArrayList<>();
                    childNodes.addAll(Def);
                    childNodes.addAll(Pred);

                    boolean unite = false;
                    for(DynamicNode DN : childNodes){
                        if(DN.getReachableStmt().containsAll(Reach)){
//                    Unite n' and v
//                            System.out.println("unite " + i + " and " + DN.getLineNumber());
                            CurrentNode.putIfAbsent(i, new ArrayList<>());
                            CurrentNode.get(i).add(DN);
                            unite = true;
                            break;
                        }
                    }

                    if(!unite){
                        CurrentNode.putIfAbsent(i, new ArrayList<>());
                        CurrentNode.get(i).add(newDn);
                    }

                    /*ddg.addVertex(GN);
                    for(DynamicNode dynamicNode : Def){
//                        ddg.addControlDependencyEdge(GN, dynamicNode);
                    }*/

                    if( !GN.getDefinedVariables().isEmpty() ){
//                update DefnNode
                        Set<String> definedVar = GN.getDefinedVariables();
                        for(String s : definedVar){
                            DefnNode.put(s, CurrentNode.get(i).get(CurrentNode.get(i).size() - 1));
                        }
                    }

                    if( !_cdg.outgoingEdgesOf(GN).isEmpty() /* GN is control Node */) {
//                update PrednNode
                        PrednNode.put(i, CurrentNode.get(i).get(CurrentNode.get(i).size() - 1));
                    }

//                    System.out.println("PrednNode: " + PrednNode);
                }
            }

        }
    }

    public Set<Integer> dynamicSlice(int lineNumber){

        Set<Integer> ret = new HashSet<>();

        Set<DynamicNode> queue = new HashSet<>(CurrentNode.get(lineNumber));
        Set<DynamicNode> visited = new HashSet<>();
        while(!queue.isEmpty()){
            Set<DynamicNode> current = new HashSet<>(queue);
            visited.addAll(current);
            queue.clear();
            for(DynamicNode dn : current){
                ret.addAll(dn.getReachableStmt());
                System.out.println("sentence " + dn.getLineNumber() + " add " + dn.getReachableStmt());
                for(DynamicNode dnn : dn.getDef()){
                    if(!visited.contains(dnn)){
                        queue.add(dnn);
                    }
                }
                for(DynamicNode dnn : dn.getPred()){
                    if(!visited.contains(dnn)){
                        queue.add(dnn);
                    }
                }

            }
        }
//
//        for(DynamicNode dn : CurrentNode.get(lineNumber)){
//            ret.addAll(dn.getReachableStmt());
//        }

        return ret;
    }
}
