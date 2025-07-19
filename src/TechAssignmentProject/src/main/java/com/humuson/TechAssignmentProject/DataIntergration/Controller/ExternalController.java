package com.humuson.TechAssignmentProject.DataIntergration.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.humuson.TechAssignmentProject.Common.DAO.Orders;
import com.humuson.TechAssignmentProject.DataIntergration.Service.ExternalService;

@RestController
@RequestMapping("/api")
public class ExternalController {

    @Autowired
    private ExternalService externalService;

    @GetMapping("/test-connection")
    public String testConnection() {
        return "Connection successful";
    }

    // 외부에서 데이터 연동 요청시 데이터 전송
    @GetMapping("/send-data")
    public List<Orders> sendData() {
        List<Orders> orderList = externalService.sendData();
        return orderList;
    }

    // 외부에서 데이터 연동 요청시 데이터 가져오기
    @PostMapping("/fetch-data")
    public List<Orders> fetchData(@RequestBody String param) {
        System.out.println("param: " + param);
        List<Orders> orderList = externalService.fetchData(param);
        System.out.println("orderList: " + orderList);
        return orderList;
    }

}
