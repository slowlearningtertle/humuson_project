package com.humuson.TechAssignmentProject.Exeption.http;

import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;

public class ApiTimeoutException extends BaseException {
    
    private final String url;
    private final int timeoutSeconds;
    
    public ApiTimeoutException(String url, int timeoutSeconds) {
        super(ErrorCode.API_TIMEOUT, 
              String.format("API 호출 타임아웃: URL=%s, Timeout=%d초", url, timeoutSeconds));
        this.url = url;
        this.timeoutSeconds = timeoutSeconds;
    }
    
    public ApiTimeoutException(String url, int timeoutSeconds, Throwable cause) {
        super(ErrorCode.API_TIMEOUT, 
              String.format("API 호출 타임아웃: URL=%s, Timeout=%d초", url, timeoutSeconds), 
              cause);
        this.url = url;
        this.timeoutSeconds = timeoutSeconds;
    }
    
    public String getUrl() {
        return url;
    }
    
    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }
} 