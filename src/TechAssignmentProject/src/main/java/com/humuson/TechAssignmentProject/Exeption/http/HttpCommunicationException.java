package com.humuson.TechAssignmentProject.Exeption.http;

import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;

public class HttpCommunicationException extends BaseException {
    
    public HttpCommunicationException(String message) {
        super(ErrorCode.HTTP_CONNECTION_FAILED, message);
    }
    
    public HttpCommunicationException(String message, Throwable cause) {
        super(ErrorCode.HTTP_CONNECTION_FAILED, message, cause);
    }
    
    public HttpCommunicationException(String url, String message) {
        super(ErrorCode.HTTP_CONNECTION_FAILED, "URL: " + url + " - " + message);
    }
} 