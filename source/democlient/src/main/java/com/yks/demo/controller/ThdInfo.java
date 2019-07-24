package com.yks.demo.controller;

public class ThdInfo {
    public String thdName;
    public long totalRequest;
    public long successRequest;
    public long failRequest;
    public long totalTime;
    public long startTime;
    public long endTime;
    public long maxTime = 0L;
    public long minTime = 0L;
    public StringBuilder failedMsg;
}
