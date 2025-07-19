package com.humuson.TechAssignmentProject.Exeption.data;

import java.util.Map;
import java.util.HashMap;
import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;

public class DataValidationException extends BaseException {
    
    private final Map<String, String> validationErrors;
    private final String objectType;
    
    public DataValidationException(String fieldName, String errorMessage) {
        super(ErrorCode.INVALID_DATA_TYPE, 
              String.format("데이터 검증 실패 - 필드 '%s': %s", fieldName, errorMessage));
        this.validationErrors = new HashMap<>();
        this.validationErrors.put(fieldName, errorMessage);
        this.objectType = null;
    }
    
    public DataValidationException(String fieldName, String errorMessage, String objectType) {
        super(ErrorCode.INVALID_DATA_TYPE, 
              String.format("데이터 검증 실패 - 필드 '%s': %s (객체 타입: %s)", 
                           fieldName, errorMessage, objectType));
        this.validationErrors = new HashMap<>();
        this.validationErrors.put(fieldName, errorMessage);
        this.objectType = objectType;
    }
    
    public DataValidationException(Map<String, String> validationErrors) {
        super(ErrorCode.INVALID_DATA_TYPE, 
              String.format("데이터 검증 실패: %d개의 오류가 발생했습니다", validationErrors.size()));
        this.validationErrors = validationErrors;
        this.objectType = null;
    }
    
    public DataValidationException(Map<String, String> validationErrors, String objectType) {
        super(ErrorCode.INVALID_DATA_TYPE, 
              String.format("데이터 검증 실패: %d개의 오류가 발생했습니다 (객체 타입: %s)", 
                           validationErrors.size(), objectType));
        this.validationErrors = validationErrors;
        this.objectType = objectType;
    }
    
    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
    
    public String getObjectType() {
        return objectType;
    }
    
    public void addValidationError(String fieldName, String errorMessage) {
        this.validationErrors.put(fieldName, errorMessage);
    }
} 