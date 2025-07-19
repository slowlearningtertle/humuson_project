package com.humuson.TechAssignmentProject.Order.Controller;

import com.humuson.TechAssignmentProject.Common.DAO.Orders;
import com.humuson.TechAssignmentProject.Order.Service.orderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class OrderMainController {

    @Autowired
    private orderService orderService;

    @GetMapping("/order")
    public String order(Model model) {
        System.out.println("order");
        model.addAttribute("orders", orderService.findAllOrders());
        System.out.println(model.getAttribute("orders"));
        return "order_list";
    }

    @PostMapping("/order")
    public String order(@RequestBody Orders order) {
        orderService.saveOrder(order);
        System.out.println("새 주문 저장: " + order);
        return "redirect:/order";
    }

    @DeleteMapping("/order/{orderId}")
    public String deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/order";
    }

    @PutMapping("/order/{orderId}")
    public String updateOrder(@PathVariable String orderId, @RequestBody Orders order) {
        orderService.updateOrderStatus(orderId, order.getOrderstatus());
        return "redirect:/order";
    }
}
