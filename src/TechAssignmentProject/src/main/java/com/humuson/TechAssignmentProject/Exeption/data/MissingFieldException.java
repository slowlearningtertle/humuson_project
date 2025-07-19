package com.humuson.TechAssignmentProject.Exeption.data;

import java.util.List;
import java.util.Arrays;
import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;

public class MissingFieldException extends BaseException {
    
    private final List<String> missingFields;
    private final String objectType;
    
    public MissingFieldException(String fieldName) {
        super(ErrorCode.MISSING_REQUIRED_FIELD, 
              String.format("필수 필드 누락: %s", fieldName));
        this.missingFields = Arrays.asList(fieldName);
        this.objectType = null;
    }
    
    public MissingFieldException(String fieldName, String objectType) {
        super(ErrorCode.MISSING_REQUIRED_FIELD, 
              String.format("필수 필드 누락: %s (객체 타입: %s)", fieldName, objectType));
        this.missingFields = Arrays.asList(fieldName);
        this.objectType = objectType;
    }
    
    public MissingFieldException(List<String> missingFields) {
        super(ErrorCode.MISSING_REQUIRED_FIELD, 
              String.format("필수 필드 누락: %s", String.join(", ", missingFields)));
        this.missingFields = missingFields;
        this.objectType = null;
    }
    
    public MissingFieldException(List<String> missingFields, String objectType) {
        super(ErrorCode.MISSING_REQUIRED_FIELD, 
              String.format("필수 필드 누락: %s (객체 타입: %s)", 
                           String.join(", ", missingFields), objectType));
        this.missingFields = missingFields;
        this.objectType = objectType;
    }
    
    public List<String> getMissingFields() {
        return missingFields;
    }
    
    public String getObjectType() {
        return objectType;
    }
} 