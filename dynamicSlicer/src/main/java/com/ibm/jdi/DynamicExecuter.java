package com.ibm.jdi;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.nju.boot.edges.Edge;
import com.nju.boot.graphs.dependencegraph.CDG;
import com.nju.boot.nodes.GraphNode;
import com.nju.boot.slicer.SlicerCriterion;
import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ExceptionRequest;
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
    //被测类
    private String debugClass;
    //执行记录
    private List<Integer> logOfLines;
    //标签类语句查询表
    private Map<Integer, Integer> linesOfLabels;
    //最近函数定义语句
    private Map<String, DynamicNode> DefnNode = new HashMap<>();
    //最近控制类语句
    private Map<GraphNode<?>, DynamicNode> PrednNode = new HashMap<>();
    //从行号搜寻动态结点
    private Map<Integer, List<DynamicNode>> CurrentNode = new HashMap<>();
    //从静态节点搜寻动态结点
    private Map<GraphNode<?>, List<DynamicNode>> GNToDN = new HashMap<>();

    private void setLogOfLines(){
        logOfLines = new ArrayList<>();
    }

    public VirtualMachine connectAndLaunchVM(String path, String fileName) throws Exception
    //连接JVM
    {
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

/*    public void getAllcaseLocations(@NotNull VirtualMachine vm) throws AbsentInformationException {
       List<ReferenceType> classes = vm.classesByName(debugClass);
       ReferenceType targetClass = classes.get(0);

       // 获取方法
       List<Method> methods = targetClass.methodsByName("main");
       Method method = methods.get(0);

       // 获取方法的所有行号
       List<Location> locations = method.allLineLocations();

       // 遍历所有行号，查找switch语句的case标签位置
       for (Location location : locations) {
           System.out.println("Case标签位置：" + location.lineNumber());
       }
   }*/

    public boolean executeFile(String path, String fileName, String className, String input) throws Exception
    //执行被测文件
    {
        this.setDebugClass(className);
        this.setLogOfLines();
        VirtualMachine vm = null;

        // 设置执行时间限制为60秒
        int timeoutSeconds = 60;
        Timer timer = new Timer();

        // 在这里执行你的JDI代码
        try {
            vm = this.connectAndLaunchVM(path, fileName);

            Process process = vm.process();
            OutputStream outputStream = process.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            outputStream.close();

            this.enableClassPrepareRequest(vm);
            this.enableExceptionRequest(vm);

            //计时器
            VirtualMachine finalVm = vm;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    finalVm.exit(0); // 终止程序执行
                    throw new FileTimeLimitExceedException();
                }
            }, timeoutSeconds * 1000);

            EventSet eventSet;
            //接受JVM事件
            while ((eventSet = vm.eventQueue().remove()) != null) {
                for (Event event : eventSet) {
                    if (event instanceof VMStartEvent) {
                        System.out.println("VM started.");
                    }
                    if (event instanceof ClassPrepareEvent) {
//                        设置程序入口断点
                        this.setBreakPoints(vm, (ClassPrepareEvent)event);
                    }
                    if (event instanceof BreakpointEvent) {
//                        执行Step请求
//                        this.getAllcaseLocations(vm);
                        this.enableStepRequest(vm, (BreakpointEvent)event);
                    }
                    if (event instanceof StepEvent) {
//                        根据Step更新执行记录
                        this.updatingLog((StepEvent) event);
                    }
                    if (event instanceof ExceptionEvent) {
//                        运行中错误处理
//                        System.out.println("Exception catched.");
                        vm.exit(0);
                        throw new FileRunningException();
                    }
                    vm.resume();
                }
            }
        } catch (VMDisconnectedException e) {
            System.out.println("Virtual Machine is disconnected.");
        }
        catch (IndexOutOfBoundsException e) {
            throw new FileUnexecutableException();
        }
        finally {
//            输出文件执行结果
//            InputStreamReader reader = null;
//            if (vm != null) {
//                reader = new InputStreamReader(vm.process().getInputStream());
//            }
            OutputStreamWriter writer = new OutputStreamWriter(System.out);
            String buf = "";
//            if (reader != null) {
//                reader.read(buf.toCharArray());
//            }
            writer.write(buf);
            writer.flush();
        }

        if (vm != null) {
            vm.process().destroy();
        }

        // 取消计时器
        timer.cancel();

        System.out.println("Execute over.");

        return true;
    }

    //以下是对JVM事件请求的设置

    public void enableClassPrepareRequest(@NotNull VirtualMachine vm)
    //Class准备事件
    {
        ClassPrepareRequest classPrepareRequest = vm.eventRequestManager().createClassPrepareRequest();
        classPrepareRequest.addClassFilter(debugClass);
        classPrepareRequest.enable();
    }

    public void enableExceptionRequest(@NotNull VirtualMachine vm)
    //错误事件
    {
        ExceptionRequest exceptionRequest = vm.eventRequestManager().createExceptionRequest(null, true, true);
        exceptionRequest.enable();
    }

    public void setBreakPoints(@NotNull VirtualMachine vm, @NotNull ClassPrepareEvent event) throws AbsentInformationException, IndexOutOfBoundsException
//    断点设置
    {
        ClassType classType = (ClassType) event.referenceType();
        Method methods = classType.methodsByName("main").get(0);
        List<Location> locations = methods.allLineLocations();
        Location mainEntryLocation = locations.get(0);

//        addToLogofLines(mainEntryLocation.lineNumber());

        BreakpointRequest bpReq = vm.eventRequestManager().createBreakpointRequest(mainEntryLocation);
        bpReq.enable();
    }

    public void enableStepRequest(@NotNull VirtualMachine vm, @NotNull BreakpointEvent event) throws IncompatibleThreadStateException
//    逐步调试请求设置
    {
        // first line log
        StackFrame stackFrame = event.thread().frame(0);
//        System.out.println("Variables at " + stackFrame.location().toString() +  " > ");
        addToLogofLines(stackFrame.location().lineNumber());

        StepRequest stepRequest = vm.eventRequestManager()
                .createStepRequest(event.thread(), StepRequest.STEP_LINE, StepRequest.STEP_OVER);
        stepRequest.enable();
    }

    private void addToLogofLines(Integer newLog)
//    增加执行记录，用于标签类语句特殊处理
    {
        if(linesOfLabels.containsKey(newLog)) {
            addToLogofLines(linesOfLabels.get(newLog));
        }
        logOfLines.add(newLog);
    }

    public void updatingLog(@NotNull LocatableEvent event) throws IncompatibleThreadStateException, AbsentInformationException
//    更新执行记录
    {
        StackFrame stackFrame = event.thread().frame(0);
        if(stackFrame.location().toString().contains(debugClass)) {
//            Map<LocalVariable, Value> visibleVariables = stackFrame
//                    .getValues(stackFrame.visibleVariables());
//            System.out.println("Variables at " + stackFrame.location().toString() +  " > ");
            addToLogofLines(stackFrame.location().lineNumber());
//            logOfLines.add(stackFrame.location().lineNumber());
        }
    }


   /*public Set<Integer> getLines(@NotNull GraphNode<?> GN) {
       Set<Integer> lns = new HashSet<>();

       if (GN.getAstNode() instanceof WhileStmt) {

           Range conditionRange = ((WhileStmt) GN.getAstNode()).getCondition().getRange().get();
           Range bodyRange = ((WhileStmt) GN.getAstNode()).getBody().getRange().get();

           for (int j = conditionRange.begin.line; j <= conditionRange.begin.line; j++) {
               lns.add(j);
           }

           lns.add(bodyRange.begin.line);
           lns.add(bodyRange.end.line);
       }
       else if (GN.getAstNode() instanceof ForStmt) {

           for (Expression expr :((ForStmt) GN.getAstNode()).getInitialization()) {
               Range initRange = expr.getRange().get();
               for (int j = initRange.begin.line; j <= initRange.begin.line; j++) {
                   lns.add(j);
               }
           }

           Range conditionRange = null;
           if(((ForStmt) GN.getAstNode()).getCompare().isPresent()) {
               conditionRange = ((ForStmt) GN.getAstNode()).getCompare().get().getRange().get();
               for (int j = conditionRange.begin.line; j <= conditionRange.begin.line; j++) {
                   lns.add(j);
               }
           }

           for (Expression expr :((ForStmt) GN.getAstNode()).getUpdate()) {
               Range initRange = expr.getRange().get();
               for (int j = initRange.begin.line; j <= initRange.begin.line; j++) {
                   lns.add(j);
               }
           }

           Range bodyRange = ((ForStmt) GN.getAstNode()).getBody().getRange().get();

           lns.add(bodyRange.begin.line);
           lns.add(bodyRange.end.line);
       }else if (GN.getAstNode() instanceof ForEachStmt) {
           Range varRange = ((ForEachStmt) GN.getAstNode()).getVariable().getRange().get();
           Range iteRange = ((ForEachStmt) GN.getAstNode()).getIterable().getRange().get();
           Range bodyRange = ((ForEachStmt) GN.getAstNode()).getBody().getRange().get();

           for (int j = varRange.begin.line; j <= varRange.begin.line; j++) {
               lns.add(j);
           }

           for (int j = iteRange.begin.line; j <= iteRange.begin.line; j++) {
               lns.add(j);
           }

           lns.add(bodyRange.begin.line);
           lns.add(bodyRange.end.line);
       } else if (GN.getAstNode() instanceof SwitchStmt) {
           Range selectorRange = ((SwitchStmt) GN.getAstNode()).getSelector().getRange().get();

           for (int j = selectorRange.begin.line; j <= selectorRange.begin.line; j++) {
               lns.add(j);
           }

           lns.add(GN.getAstNode().getBegin().get().line);
           lns.add(GN.getAstNode().getEnd().get().line);
       *//*} else if (GN.getAstNode() instanceof DoStmt) {
           Range bodyRange = ((DoStmt) GN.getAstNode()).getBody().getRange().get();

           lns.add(bodyRange.begin.line);
           lns.add(bodyRange.end.line);

           Range consitionRange = ((DoStmt) GN.getAstNode()).getCondition().getRange().get();

           for (int j = consitionRange.begin.line; j <= consitionRange.begin.line; j++) {
               lns.add(j);
           }
*//*
       } else if (GN.getAstNode() instanceof IfStmt) {
           Range consitionRange = ((IfStmt) GN.getAstNode()).getCondition().getRange().get();
           for (int j = consitionRange.begin.line; j <= consitionRange.begin.line; j++) {
               lns.add(j);
           }

           Range bodyRange = ((IfStmt) GN.getAstNode()).getThenStmt().getRange().get();
           lns.add(bodyRange.begin.line);
           lns.add(bodyRange.end.line);

           if(((IfStmt) GN.getAstNode()).getElseStmt().isPresent()) {
               Range elsebodyRange = ((IfStmt) GN.getAstNode()).getElseStmt().get().getRange().get();
//                System.out.println(((IfStmt) GN.getAstNode()).getElseStmt().get().getParentNode());
               lns.add(elsebodyRange.begin.line);
               lns.add(elsebodyRange.end.line);
           }


       } else if (GN.getAstNode() instanceof MethodDeclaration) {
           lns.add(GN.getAstNode().getBegin().get().line);
           lns.add(GN.getAstNode().getEnd().get().line);
       } else {
           Range range = GN.getAstNode().getRange().get();

           for (int j = range.begin.line; j <= range.end.line; j++) {
               lns.add(j);
           }

       }

       return lns;
   }*/

    public void buildingDDG(CDG _cdg) throws NoSuchElementException
//    构造动态依赖图
    {

//        System.out.println(logOfLines);

        for(int i : logOfLines){
//                        DDG
//            根据行号找到对应静态图节点
            SlicerCriterion slicerCriterion = new SlicerCriterion(null, i, _cdg);
            Set<GraphNode<?>> graphNodes = slicerCriterion.findNodeByLineNumber(_cdg);

            for(GraphNode<?> GN : graphNodes /* 行号i to 结点GN */){
//                数据依赖项
                Set<DynamicNode> Def = new HashSet<>();
//                控制依赖项
                Set<DynamicNode> Pred = new HashSet<>();

//                获取数据依赖项
                Set<String> Use = GN.getUsedVariables();
                for(String v : Use){
                    System.out.println(GN.getAstNode().getClass() + " at " + i + " Used Variable: " + v);
                    if (DefnNode.containsKey(v)) {
                        Def.add(DefnNode.get(v));
                        System.out.println("Variable " + v + " refers to " + DefnNode.get(v).getGN().getAstNode().getBegin().get().line);
//                        System.out.println("add reachable: " + DefnNode.get(v).getReachableStmt());

                    }
                }

//                获取控制依赖项
                for(Edge inEdge : _cdg.incomingEdgesOf(GN)){
                    GraphNode<?> p = _cdg.getEdgeSource(inEdge);
                    System.out.println( i + " Controled by: " + p.getAstNode().getBegin().get().line + ",type: " + p.getAstNode().getClass());
                    if(p.getAstNode() instanceof BreakStmt || p.getAstNode() instanceof ContinueStmt)
                        continue;
                    if(PrednNode.containsKey(p)){
                        Pred.add(PrednNode.get(p));
//                        System.out.println("add reachable: " + PrednNode.get(NodeId).getReachableStmt());
                    }
                }

//                设置动态结点版本号
                int version = 0;
                if(GNToDN.containsKey(GN)) {
                    version = GNToDN.get(GN).size();
                }
                DynamicNode newDn = new DynamicNode(Def, Pred, GN, version);

//                增加四种动态结点搜索表
                CurrentNode.putIfAbsent(i, new ArrayList<>());
                CurrentNode.get(i).add(newDn);

                GNToDN.putIfAbsent(GN, new ArrayList<>());
                GNToDN.get(GN).add(newDn);

//                Break/Continue特判
                if(GN.getAstNode() instanceof BreakStmt || GN.getAstNode() instanceof ContinueStmt){
                    for(Edge outEdge : _cdg.outgoingEdgesOf(GN)){
                        GraphNode<?> w = _cdg.getEdgeTarget(outEdge);
                        if (GNToDN.containsKey(w)) {
                            GNToDN.get(w).get(GNToDN.get(w).size()-1).getPred().add(newDn);
                        }
                    }
                }

                if( !GN.getDefinedVariables().isEmpty() ){
//                    System.out.println(i + " Updating DefnNode: ");
//                    更新DefnNode
                    Set<String> definedVar = GN.getDefinedVariables();
                    for(String s : definedVar){
                        DefnNode.put(s, GNToDN.get(GN).get(GNToDN.get(GN).size() - 1));
//                            System.out.println(s + " Update DefnNode : " + DefnNode.get(s).getLineNumber());
                    }
                }

                if( !_cdg.outgoingEdgesOf(GN).isEmpty() /* GN is control Node */) {
//                    更新PrednNode
                    PrednNode.put(GN, GNToDN.get(GN).get(GNToDN.get(GN).size() - 1));
                    System.out.println("Upadte PrednNode: " + PrednNode.get(GN).getGN().getAstNode().getBegin().get().line);
                }

            }

        }
    }

    public Set<Node> dynamicSlice(int lineNumber)
//    切片功能
    {

        Set<Node> ret = new HashSet<>();

//        遍历动态依赖图，将依赖语句的AST结点加入Set
        Set<DynamicNode> queue = new HashSet<>();
        if(CurrentNode.containsKey(lineNumber))
            queue.addAll(CurrentNode.get(lineNumber));
        Set<DynamicNode> visited = new HashSet<>();
        while(!queue.isEmpty()){
            Set<DynamicNode> current = new HashSet<>(queue);
            visited.addAll(current);
            queue.clear();
            for(DynamicNode dn : current){
                ret.add(dn.getGN().getAstNode());
//                System.out.println("sentence " + dn.getGN().getAstNode().getBegin().get().line);
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
