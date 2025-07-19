package com.humuson.TechAssignmentProject.Common.DAO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private String orderid; // 주문 ID
    private String orderdate; // 주문 날짜
    private String orderstatus; // 주문 상태
    private String itemid; // 상품 ID
    private String customerid; // 고객 ID
    private String totalamount; // 총 금액
    private String changedate; // 수정시간
}
