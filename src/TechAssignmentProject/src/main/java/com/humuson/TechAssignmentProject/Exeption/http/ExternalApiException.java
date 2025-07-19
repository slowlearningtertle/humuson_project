package com.humuson.TechAssignmentProject.Exeption.http;

import org.springframework.http.HttpStatus;
import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;

public class ExternalApiException extends BaseException {
    
    private final String url;
    private final HttpStatus httpStatus;
    private final String responseBody;
    
    public ExternalApiException(String url, HttpStatus httpStatus) {
        super(ErrorCode.INVALID_HTTP_STATUS, 
              String.format("외부 API 오류: URL=%s, Status=%s", url, httpStatus));
        this.url = url;
        this.httpStatus = httpStatus;
        this.responseBody = null;
    }
    
    public ExternalApiException(String url, HttpStatus httpStatus, String responseBody) {
        super(ErrorCode.INVALID_HTTP_STATUS, 
              String.format("외부 API 오류: URL=%s, Status=%s, Response=%s", url, httpStatus, responseBody));
        this.url = url;
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
    }
    
    public ExternalApiException(String url, int statusCode, String responseBody) {
        super(ErrorCode.INVALID_HTTP_STATUS, 
              String.format("외부 API 오류: URL=%s, Status=%d, Response=%s", url, statusCode, responseBody));
        this.url = url;
        this.httpStatus = HttpStatus.valueOf(statusCode);
        this.responseBody = responseBody;
    }
    
    public String getUrl() {
        return url;
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    public String getResponseBody() {
        return responseBody;
    }
    
    public int getStatusCode() {
        return httpStatus.value();
    }
} 