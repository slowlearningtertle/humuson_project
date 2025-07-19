package com.humuson.TechAssignmentProject.Exeption.base;

public abstract class BaseException extends RuntimeException {
    
    private final ErrorCode errorCode;
    private final String timestamp;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public BaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getErrorCodeValue() {
        return errorCode.getCode();
    }
} 