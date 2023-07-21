package com.nju.boot.edges;

/**
 * CallGraph的调用边
 * src：调用方法的方法
 * target：被调用的方法
 */
public class CallEdge extends Edge{
    /**
     * 调用次数的信息
     */
    private int times = 1;
    public void timesIncrement(){
        times++;
    }
    public int getTimes(){
        return times;
    }
}
