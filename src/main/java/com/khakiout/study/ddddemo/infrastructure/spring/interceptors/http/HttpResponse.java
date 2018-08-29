package com.khakiout.study.ddddemo.infrastructure.spring.interceptors.http;


public class HttpResponse {
    private long startTime;
    private long endTime;
    private String duration;
    private int statusCode;
    private String statusMessage;

    public HttpResponse() {
        this.startTime = 0;
        this.endTime = 0;
        this.duration = "";
        this.statusCode = 0;
        this.statusMessage = "";
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "{ " +
                " duration = '" + duration + '\'' +
                ", statusCode = " + statusCode +
                ", statusMessage = '" + statusMessage + '\'' +
                " }";
    }
}
