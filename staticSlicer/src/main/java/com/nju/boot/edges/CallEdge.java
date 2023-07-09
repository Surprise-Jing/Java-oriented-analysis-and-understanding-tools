package com.nju.boot.edges;

public class CallEdge extends Edge{
    //调用次数初始化为1
    private int times = 1;
    public void timesIncrement(){
        times++;
    }
    public int getTimes(){
        return times;
    }
}
