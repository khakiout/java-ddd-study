package com.khakiout.study.ddddemo.infrastructure.spring.interceptors.http;

public class HttpLog {

    public HttpRequest request;
    public HttpResponse response;

    public HttpLog() {
        this.request = new HttpRequest();
        this.response = new HttpResponse();
    }

    @Override
    public String toString() {
        return "HttpLog { " +
            "request = " + request +
            ", response = " + response +
            " }";
    }
}
