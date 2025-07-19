package com.humuson.TechAssignmentProject.Exeption.sync;

import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;

public class DuplicateDataException extends BaseException {
    
    private final String duplicateKey;
    private final String dataType;
    private final Object existingData;
    
    public DuplicateDataException(String duplicateKey, String dataType) {
        super(ErrorCode.DUPLICATE_ORDER_ID, 
              String.format("중복 데이터 발견: 키=%s, 타입=%s", duplicateKey, dataType));
        this.duplicateKey = duplicateKey;
        this.dataType = dataType;
        this.existingData = null;
    }
    
    public DuplicateDataException(String duplicateKey, String dataType, Object existingData) {
        super(ErrorCode.DUPLICATE_ORDER_ID, 
              String.format("중복 데이터 발견: 키=%s, 타입=%s", duplicateKey, dataType));
        this.duplicateKey = duplicateKey;
        this.dataType = dataType;
        this.existingData = existingData;
    }
    
    public DuplicateDataException(String duplicateKey, String dataType, String message) {
        super(ErrorCode.DUPLICATE_ORDER_ID, 
              String.format("중복 데이터 발견: 키=%s, 타입=%s - %s", duplicateKey, dataType, message));
        this.duplicateKey = duplicateKey;
        this.dataType = dataType;
        this.existingData = null;
    }
    
    public String getDuplicateKey() {
        return duplicateKey;
    }
    
    public String getDataType() {
        return dataType;
    }
    
    public Object getExistingData() {
        return existingData;
    }
} 