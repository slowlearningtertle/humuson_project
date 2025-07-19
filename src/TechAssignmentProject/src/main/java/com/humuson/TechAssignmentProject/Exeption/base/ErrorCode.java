package com.humuson.TechAssignmentProject.Exeption.base;

public enum ErrorCode {
    // HTTP 통신 관련 (1000번대)
    HTTP_CONNECTION_FAILED(1001, "HTTP 연결 실패"),
    API_TIMEOUT(1002, "API 응답 타임아웃"),
    INVALID_HTTP_STATUS(1003, "잘못된 HTTP 상태 코드"),
    SSL_CERTIFICATE_ERROR(1004, "SSL 인증서 오류"),
    
    // JSON 처리 관련 (2000번대)
    JSON_PARSING_ERROR(2001, "JSON 파싱 오류"),
    MISSING_REQUIRED_FIELD(2002, "필수 필드 누락"),
    INVALID_DATA_TYPE(2003, "잘못된 데이터 타입"),
    JSON_SCHEMA_VALIDATION_FAILED(2004, "JSON 스키마 검증 실패"),
    
    // 데이터 동기화 관련 (3000번대)
    DATA_SYNC_FAILED(3001, "데이터 동기화 실패"),
    DUPLICATE_ORDER_ID(3002, "중복된 주문 ID"),
    DATA_INTEGRITY_VIOLATION(3003, "데이터 무결성 위반"),
    SYNC_TIMEOUT(3004, "동기화 타임아웃"),
    
    // 일반적인 시스템 오류 (9000번대)
    INTERNAL_SERVER_ERROR(9001, "내부 서버 오류"),
    UNKNOWN_ERROR(9999, "알 수 없는 오류");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
} 