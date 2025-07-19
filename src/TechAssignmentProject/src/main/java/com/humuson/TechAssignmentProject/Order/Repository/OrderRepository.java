package com.humuson.TechAssignmentProject.Order.Repository;

import java.util.List;
import java.util.Optional;

import com.humuson.TechAssignmentProject.Common.DAO.Orders;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    void save(Orders order); // 주문 저장 (새로 생성 또는 업데이트)
    Optional<Orders> findById(String orderId); // ID로 주문 조회
    boolean updateStatus(String orderId, String newStatus); // 주문 상태 업데이트
    boolean deleteById(String orderId); // ID로 주문 삭제
    List<Orders> findAll(); // 모든 주문 조회
}
