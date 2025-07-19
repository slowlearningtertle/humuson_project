package com.humuson.TechAssignmentProject.Exeption.sync;

import com.humuson.TechAssignmentProject.Exeption.base.BaseException;
import com.humuson.TechAssignmentProject.Exeption.base.ErrorCode;

public class DataSyncException extends BaseException {
    
    private final String syncOperation;
    private final String sourceSystem;
    private final String targetSystem;
    
    public DataSyncException(String syncOperation, String message) {
        super(ErrorCode.DATA_SYNC_FAILED, 
              String.format("데이터 동기화 실패 - 작업: %s, 오류: %s", syncOperation, message));
        this.syncOperation = syncOperation;
        this.sourceSystem = null;
        this.targetSystem = null;
    }
    
    public DataSyncException(String syncOperation, String sourceSystem, String targetSystem, String message) {
        super(ErrorCode.DATA_SYNC_FAILED, 
              String.format("데이터 동기화 실패 - 작업: %s, 소스: %s, 타겟: %s, 오류: %s", 
                           syncOperation, sourceSystem, targetSystem, message));
        this.syncOperation = syncOperation;
        this.sourceSystem = sourceSystem;
        this.targetSystem = targetSystem;
    }
    
    public DataSyncException(String syncOperation, String message, Throwable cause) {
        super(ErrorCode.DATA_SYNC_FAILED, 
              String.format("데이터 동기화 실패 - 작업: %s, 오류: %s", syncOperation, message), 
              cause);
        this.syncOperation = syncOperation;
        this.sourceSystem = null;
        this.targetSystem = null;
    }
    
    public String getSyncOperation() {
        return syncOperation;
    }
    
    public String getSourceSystem() {
        return sourceSystem;
    }
    
    public String getTargetSystem() {
        return targetSystem;
    }
} 