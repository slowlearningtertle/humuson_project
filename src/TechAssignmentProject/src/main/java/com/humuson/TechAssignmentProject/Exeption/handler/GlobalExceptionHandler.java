package com.humuson.TechAssignmentProject.Exeption.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;
import com.humuson.TechAssignmentProject.Exeption.http.HttpCommunicationException;
import com.humuson.TechAssignmentProject.Exeption.http.ApiTimeoutException;
import com.humuson.TechAssignmentProject.Exeption.http.ExternalApiException;
import com.humuson.TechAssignmentProject.Exeption.data.JsonParsingException;
import com.humuson.TechAssignmentProject.Exeption.data.MissingFieldException;
import com.humuson.TechAssignmentProject.Exeption.data.DataValidationException;
import com.humuson.TechAssignmentProject.Exeption.sync.DuplicateDataException;
import com.humuson.TechAssignmentProject.Exeption.sync.DataSyncException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    // 커스텀 예외 처리
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handleBaseException(BaseException ex, WebRequest request) {
        logger.error("BaseException 발생: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        
        HttpStatus status = determineHttpStatus(ex);
        return new ResponseEntity<>(response, status);
    }
    
    // HTTP 통신 관련 예외 처리
    @ExceptionHandler(HttpCommunicationException.class)
    public ResponseEntity<ExceptionResponse> handleHttpCommunicationException(HttpCommunicationException ex, WebRequest request) {
        logger.error("HTTP 통신 오류: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @ExceptionHandler(ApiTimeoutException.class)
    public ResponseEntity<ExceptionResponse> handleApiTimeoutException(ApiTimeoutException ex, WebRequest request) {
        logger.error("API 타임아웃: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }
    
    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ExceptionResponse> handleExternalApiException(ExternalApiException ex, WebRequest request) {
        logger.error("외부 API 오류: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode());
        return new ResponseEntity<>(response, status);
    }
    
    // JSON 처리 관련 예외 처리
    @ExceptionHandler(JsonParsingException.class)
    public ResponseEntity<ExceptionResponse> handleJsonParsingException(JsonParsingException ex, WebRequest request) {
        logger.error("JSON 파싱 오류: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<ExceptionResponse> handleMissingFieldException(MissingFieldException ex, WebRequest request) {
        logger.error("필수 필드 누락: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<ExceptionResponse> handleDataValidationException(DataValidationException ex, WebRequest request) {
        logger.error("데이터 검증 실패: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // 데이터 동기화 관련 예외 처리
    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateDataException(DuplicateDataException ex, WebRequest request) {
        logger.error("중복 데이터: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(DataSyncException.class)
    public ResponseEntity<ExceptionResponse> handleDataSyncException(DataSyncException ex, WebRequest request) {
        logger.error("데이터 동기화 실패: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = ExceptionResponse.from(ex, request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // 일반적인 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex, WebRequest request) {
        logger.error("예상치 못한 오류 발생: {}", ex.getMessage(), ex);
        
        ExceptionResponse response = new ExceptionResponse(
            ErrorCode.UNKNOWN_ERROR.getCode(),
            "서버 내부 오류가 발생했습니다.",
            request.getDescription(false)
        );
        response.addDetail("originalMessage", ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // HTTP 상태 코드 결정
    private HttpStatus determineHttpStatus(BaseException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        
        switch (errorCode) {
            case HTTP_CONNECTION_FAILED:
            case API_TIMEOUT:
                return HttpStatus.SERVICE_UNAVAILABLE;
            case INVALID_HTTP_STATUS:
                return HttpStatus.BAD_GATEWAY;
            case JSON_PARSING_ERROR:
            case MISSING_REQUIRED_FIELD:
            case INVALID_DATA_TYPE:
            case JSON_SCHEMA_VALIDATION_FAILED:
                return HttpStatus.BAD_REQUEST;
            case DUPLICATE_ORDER_ID:
                return HttpStatus.CONFLICT;
            case DATA_SYNC_FAILED:
            case DATA_INTEGRITY_VIOLATION:
            case SYNC_TIMEOUT:
            case INTERNAL_SERVER_ERROR:
            case UNKNOWN_ERROR:
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
} 