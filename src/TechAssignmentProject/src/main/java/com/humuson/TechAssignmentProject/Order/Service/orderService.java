package com.humuson.TechAssignmentProject.Order.Service;

import java.util.List;
import java.util.Optional;

import com.humuson.TechAssignmentProject.Common.DAO.Orders;
import com.humuson.TechAssignmentProject.Order.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class orderService {

    private OrderRepository orderRepository;

    public orderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Orders order) {
        orderRepository.save(order);
    }

    public Optional<Orders> findOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public boolean updateOrderStatus(String orderId, String newStatus) {
        return orderRepository.updateStatus(orderId, newStatus);
    }

    public boolean deleteOrder(String orderId) {
        return orderRepository.deleteById(orderId);
    }

    public List<Orders> findAllOrders() {
        return orderRepository.findAll();
    }
}
