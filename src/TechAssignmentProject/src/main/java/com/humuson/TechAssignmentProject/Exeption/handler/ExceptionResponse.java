package com.humuson.TechAssignmentProject.Exeption.handler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.data.MissingFieldException;
import com.humuson.TechAssignmentProject.Exeption.data.DataValidationException;
import com.humuson.TechAssignmentProject.Exeption.sync.DuplicateDataException;
import com.humuson.TechAssignmentProject.Exeption.http.ExternalApiException;
import com.humuson.TechAssignmentProject.Exeption.http.ApiTimeoutException;

public class ExceptionResponse {
    private final int errorCode;
    private final String message;
    private final String timestamp;
    private final String path;
    private final Map<String, Object> details;
    
    public ExceptionResponse(int errorCode, String message, String path) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
        this.path = path;
        this.details = new HashMap<>();
    }
    
    public ExceptionResponse(int errorCode, String message, String path, Map<String, Object> details) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
        this.path = path;
        this.details = details != null ? details : new HashMap<>();
    }
    
    public static ExceptionResponse from(BaseException exception, String path) {
        ExceptionResponse response = new ExceptionResponse(
            exception.getErrorCodeValue(),
            exception.getMessage(),
            path
        );
        
        // 예외 타입에 따른 추가 정보 설정
        if (exception instanceof MissingFieldException) {
            MissingFieldException missingFieldException = (MissingFieldException) exception;
            response.addDetail("missingFields", missingFieldException.getMissingFields());
            response.addDetail("objectType", missingFieldException.getObjectType());
        } else if (exception instanceof DataValidationException) {
            DataValidationException validationException = (DataValidationException) exception;
            response.addDetail("validationErrors", validationException.getValidationErrors());
            response.addDetail("objectType", validationException.getObjectType());
        } else if (exception instanceof DuplicateDataException) {
            DuplicateDataException duplicateException = (DuplicateDataException) exception;
            response.addDetail("duplicateKey", duplicateException.getDuplicateKey());
            response.addDetail("dataType", duplicateException.getDataType());
        } else if (exception instanceof ExternalApiException) {
            ExternalApiException apiException = (ExternalApiException) exception;
            response.addDetail("url", apiException.getUrl());
            response.addDetail("statusCode", apiException.getStatusCode());
            response.addDetail("responseBody", apiException.getResponseBody());
        } else if (exception instanceof ApiTimeoutException) {
            ApiTimeoutException timeoutException = (ApiTimeoutException) exception;
            response.addDetail("url", timeoutException.getUrl());
            response.addDetail("timeoutSeconds", timeoutException.getTimeoutSeconds());
        }
        
        return response;
    }
    
    public void addDetail(String key, Object value) {
        this.details.put(key, value);
    }
    
    // Getters
    public int getErrorCode() {
        return errorCode;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public String getPath() {
        return path;
    }
    
    public Map<String, Object> getDetails() {
        return details;
    }
} 