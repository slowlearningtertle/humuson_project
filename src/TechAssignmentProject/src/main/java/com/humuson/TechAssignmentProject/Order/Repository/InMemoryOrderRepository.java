package com.humuson.TechAssignmentProject.Order.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.humuson.TechAssignmentProject.Common.DAO.Orders;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

	private Map<String, Orders> orders = new HashMap<>();

	@Override
	public void save(Orders order) {
		if (orders.containsKey(order.getOrderid())) {
            System.out.println("[InMemory] 경고: 주문 ID " + order.getOrderid() + "가 이미 존재합니다. 데이터를 덮어씁니다.");
        }
        orders.put(order.getOrderid(), order);
        System.out.println("[InMemory] 주문 ID " + order.getOrderid() + " 저장 완료.");
	}

	@Override
	public Optional<Orders> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
	}

	@Override
	public boolean updateStatus(String orderId, String newStatus) {
		Optional<Orders> optionalOrder = findById(orderId);
        if (optionalOrder.isPresent()) { // 주문이 존재하는지 확인
            Orders order = optionalOrder.get();
            order.setOrderstatus(newStatus); // Lombok @Setter 덕분에 직접 호출 가능
            System.out.println("[InMemory] 주문 ID " + orderId + "의 상태가 " + newStatus + "(으)로 업데이트되었습니다.");
            return true;
        }
        System.out.println("[InMemory] 오류: 주문 ID " + orderId + "를 찾을 수 없습니다.");
        return false;
	}

	@Override
	public boolean deleteById(String orderId) {
		if (orders.containsKey(orderId)) {
            orders.remove(orderId);
            System.out.println("[InMemory] 주문 ID " + orderId + " 삭제 완료.");
            return true;
        }
        System.out.println("[InMemory] 오류: 주문 ID " + orderId + "를 찾을 수 없습니다.");
        return false;
	}

	@Override
	public List<Orders> findAll() {
		return new ArrayList<>(orders.values());
	}

}
