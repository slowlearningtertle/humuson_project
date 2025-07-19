package com.humuson.TechAssignmentProject.Exeption.util;

import com.humuson.TechAssignmentProject.Common.DAO.Orders;
import java.util.List;
import java.util.ArrayList;

/**
 * 예외 처리를 테스트하기 위한 유틸리티 클래스
 */
public class ExceptionTestUtil {
    
    /**
     * 잘못된 주문 데이터 생성 (필수 필드 누락)
     */
    public static Orders createInvalidOrder() {
        return new Orders(null, "2025-01-01", "대기중", "ITEM001", "CUST001", "10000", "2025-01-01T10:00:00");
    }
    
    /**
     * 부분적으로 잘못된 주문 데이터 생성
     */
    public static Orders createPartiallyInvalidOrder() {
        return new Orders("ORD001", "", "대기중", "ITEM001", "", "10000", "2025-01-01T10:00:00");
    }
    
    /**
     * 유효한 주문 데이터 생성
     */
    public static Orders createValidOrder() {
        return new Orders("ORD001", "2025-01-01", "대기중", "ITEM001", "CUST001", "10000", "2025-01-01T10:00:00");
    }
    
    /**
     * 테스트용 주문 리스트 생성
     */
    public static List<Orders> createTestOrderList() {
        List<Orders> orders = new ArrayList<>();
        orders.add(new Orders("ORD001", "2025-01-01", "대기중", "ITEM001", "CUST001", "10000", "2025-01-01T10:00:00"));
        orders.add(new Orders("ORD002", "2025-01-02", "배송중", "ITEM002", "CUST002", "25000", "2025-01-02T11:30:00"));
        return orders;
    }
    
    /**
     * 잘못된 JSON 문자열 생성
     */
    public static String createInvalidJson() {
        return "{ \"orderid\": \"ORD001\", \"orderdate\": \"2025-01-01\", \"orderstatus\": }";
    }
    
    /**
     * 필수 필드가 누락된 JSON 문자열 생성
     */
    public static String createMissingFieldJson() {
        return "{ \"orderdate\": \"2025-01-01\", \"orderstatus\": \"대기중\" }";
    }
} 