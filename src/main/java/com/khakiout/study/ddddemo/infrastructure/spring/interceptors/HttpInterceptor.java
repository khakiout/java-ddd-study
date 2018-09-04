package com.khakiout.study.ddddemo.infrastructure.spring.interceptors;

import com.khakiout.study.ddddemo.infrastructure.spring.interceptors.http.HttpLog;
import com.khakiout.study.ddddemo.infrastructure.spring.interceptors.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

    private HttpLog httpLog;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        httpLog = new HttpLog();

        // record startTime
        httpLog.response.setStartTime(System.currentTimeMillis());

        httpLog.request = new HttpRequest(request);

        // if returned false, we need to make sure 'response' is sent
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        // record endTime
        httpLog.response.setEndTime(System.currentTimeMillis());

        // get diff for responseTime
        httpLog.response
            .setDuration(httpLog.response.getEndTime() - httpLog.response.getStartTime() + " ms");

        httpLog.response.setStatusCode(response.getStatus());
        httpLog.response.setStatusMessage(
            org.apache.commons.httpclient.HttpStatus.getStatusText(response.getStatus()));

        // log level should be set accordingly with HttpStatus
        if (response.getStatus() >= 100 && response.getStatus() < 300) {
            logger.info(httpLog.toString());
        }
        if (response.getStatus() >= 300 && response.getStatus() < 400) {
            logger.warn(httpLog.toString());
        }
        if (response.getStatus() >= 400) {
            logger.error(httpLog.toString());
        }

        httpLog = null;
        super.afterCompletion(request, response, handler, ex);
    }
}
