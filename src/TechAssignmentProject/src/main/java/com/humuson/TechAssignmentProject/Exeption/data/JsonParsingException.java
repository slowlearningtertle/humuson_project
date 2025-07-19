package com.humuson.TechAssignmentProject.Exeption.data;

import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;

public class JsonParsingException extends BaseException {
    
    private final String jsonData;
    private final String fieldName;
    
    public JsonParsingException(String message, String jsonData) {
        super(ErrorCode.JSON_PARSING_ERROR, "JSON 파싱 오류: " + message);
        this.jsonData = jsonData;
        this.fieldName = null;
    }
    
    public JsonParsingException(String message, String jsonData, Throwable cause) {
        super(ErrorCode.JSON_PARSING_ERROR, "JSON 파싱 오류: " + message, cause);
        this.jsonData = jsonData;
        this.fieldName = null;
    }
    
    public JsonParsingException(String fieldName, String message, String jsonData) {
        super(ErrorCode.JSON_PARSING_ERROR, 
              String.format("JSON 파싱 오류 - 필드 '%s': %s", fieldName, message));
        this.jsonData = jsonData;
        this.fieldName = fieldName;
    }
    
    public String getJsonData() {
        return jsonData;
    }
    
    public String getFieldName() {
        return fieldName;
    }
} 