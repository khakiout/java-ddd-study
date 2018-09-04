package com.khakiout.study.ddddemo.infrastructure.spring.interceptors.http;

import javax.servlet.http.HttpServletRequest;

public class HttpRequest {

    private String host;
    private String userAgent;
    private String accept;
    private String acceptLanguage;
    private String acceptEncoding;
    private String connection;
    private String requestURI;
    private String requestURL;
    private String method;

    public HttpRequest() {
        this.host = "";
        this.userAgent = "";
        this.accept = "";
        this.acceptLanguage = "";
        this.acceptEncoding = "";
        this.connection = "";
        this.requestURI = "";
        this.requestURL = "";
        this.method = "";
    }

    public HttpRequest(HttpServletRequest request) {
        this.host = request.getHeader("host");
        this.userAgent = request.getHeader("user-agent");
        this.accept = request.getHeader("accept");
        this.acceptLanguage = request.getHeader("accept-language");
        this.acceptEncoding = request.getHeader("accept-encoding");
        this.connection = request.getHeader("connection");
        this.requestURI = request.getRequestURI();
        this.requestURL = request.getRequestURL().toString();
        this.method = request.getMethod();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "{ " +
            " host = '" + host + '\'' +
            ", userAgent = '" + userAgent + '\'' +
            ", accept = '" + accept + '\'' +
            ", acceptLanguage = '" + acceptLanguage + '\'' +
            ", acceptEncoding = '" + acceptEncoding + '\'' +
            ", connection = '" + connection + '\'' +
            ", requestURI = '" + requestURI + '\'' +
            ", method = '" + method + '\'' +
            " }";
    }
}
