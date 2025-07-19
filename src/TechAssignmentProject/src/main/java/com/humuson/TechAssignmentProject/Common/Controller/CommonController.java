package com.humuson.TechAssignmentProject.Common.Controller;

import com.humuson.TechAssignmentProject.Order.Service.orderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class CommonController {
    
    @Autowired
    private orderService orderService;
    
    @GetMapping("/")
    public String index(Model model) {
        System.out.println("메인 페이지 로드");
        model.addAttribute("orders", orderService.findAllOrders());
        System.out.println(model.getAttribute("orders"));
        return "index";
    }
}
