package com.humuson.TechAssignmentProject.DataIntergration.Service;

// 자바
import java.util.List;
import java.util.ArrayList;

// 스프링 익셉션 
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

// 프로젝트 익셉션
import com.humuson.TechAssignmentProject.Exeption.http.HttpCommunicationException;
import com.humuson.TechAssignmentProject.Exeption.http.ExternalApiException;
import com.humuson.TechAssignmentProject.Exeption.data.MissingFieldException;
import com.humuson.TechAssignmentProject.Exeption.data.DataValidationException;
import com.humuson.TechAssignmentProject.Exeption.sync.DataSyncException;

// 스프링
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

// JSON 파싱
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// 로깅
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 프로젝트
import com.humuson.TechAssignmentProject.Common.DAO.Orders;
import com.humuson.TechAssignmentProject.Order.Repository.OrderRepository;

@Service
public class ExternalService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // 외부 API 주소
    private String url = "http://localhost:8080/api/orders";

    /**
     * 외부 API 요청
     * 
     * 외부 api에서 요청하여 데이터를 받아와 데이터 검증 및 저장
     * 
     * @param param
     * @return
     */
    public List<Orders> fetchData(String param) {
        
        try {
            
            List<Orders> orderList = objectMapper.readValue(param, new TypeReference<List<Orders>>() {});
            if (orderList == null) {
                logger.warn("API 응답 본문이 null입니다. 빈 리스트를 반환합니다.");
                return new ArrayList<>();
            }

            // 데이터 검증 및 저장
            for (Orders order : orderList) {
                validateOrder(order);
                orderRepository.save(order);
                logger.debug("주문 저장 완료: {}", order.getOrderid());
            }

            logger.info("외부 API 호출 성공: {}개의 주문 데이터를 가져왔습니다.", orderList.size());
            return orderList;

        } catch (Exception e) {
            logger.error("예상치 못한 오류: URL={}, 오류={}", url, e.getMessage(), e);
            throw new DataSyncException("fetchData", "외부 시스템", "내부 시스템", 
                "데이터 가져오기 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 외부 api에 데이터 전송
     * 
     * @return 전송된 주문 목록
     */
    public List<Orders> sendData() {
        try {
            logger.info("외부 API 데이터 전송 시작: URL={}", url);
            
            // 내부 데이터베이스에서 모든 주문 데이터 조회
            List<Orders> orderList = orderRepository.findAll();
            
            if (orderList.isEmpty()) {
                logger.warn("전송할 주문 데이터가 없습니다.");
                return new ArrayList<>();
            }
            
            // 외부 API로 데이터 전송 (POST 요청)
            ResponseEntity<List<Orders>> response = restTemplate.exchange(
                url, HttpMethod.POST, 
                new org.springframework.http.HttpEntity<>(orderList), 
                new ParameterizedTypeReference<List<Orders>>() {}
            );
            
            // HTTP 상태 코드 검증
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ExternalApiException(url, response.getStatusCode().value(), 
                    "API 전송이 성공적이지 않습니다. 상태 코드: " + response.getStatusCode().value());
            }
            
            List<Orders> sentOrders = response.getBody();
            if (sentOrders == null) {
                logger.warn("API 응답 본문이 null입니다. 빈 리스트를 반환합니다.");
                return new ArrayList<>();
            }
            
            logger.info("외부 API 데이터 전송 성공: {}개의 주문 데이터를 전송했습니다.", sentOrders.size());
            return sentOrders;
            
        } catch (ResourceAccessException e) {
            logger.error("HTTP 연결 실패: URL={}, 오류={}", url, e.getMessage());
            throw new HttpCommunicationException("연결할 수 없습니다: " + e.getMessage(), e);
            
        } catch (HttpClientErrorException e) {
            logger.error("HTTP 클라이언트 오류: URL={}, 상태={}, 응답={}", 
                        url, e.getStatusCode(), e.getResponseBodyAsString());
            throw new ExternalApiException(url, e.getStatusCode().value(), e.getResponseBodyAsString());
            
        } catch (HttpServerErrorException e) {
            logger.error("HTTP 서버 오류: URL={}, 상태={}, 응답={}", 
                        url, e.getStatusCode(), e.getResponseBodyAsString());
            throw new ExternalApiException(url, e.getStatusCode().value(), e.getResponseBodyAsString());
            
        } catch (Exception e) {
            logger.error("예상치 못한 오류: URL={}, 오류={}", url, e.getMessage(), e);
            throw new DataSyncException("sendData", "외부 시스템", "내부 시스템", 
                "데이터 전송 중 오류 발생: " + e.getMessage());
        }
    }



    /**
     * 주문 데이터 검증
     */
    private void validateOrder(Orders order) {
        if (order == null) {
            throw new DataValidationException("order", "주문 객체가 null입니다.", "Orders");
        }

        // 필수 필드 검증
        if (order.getOrderid() == null || order.getOrderid().trim().isEmpty()) {
            throw new MissingFieldException("orderid", "Orders");
        }
        if (order.getOrderdate() == null || order.getOrderdate().trim().isEmpty()) {
            throw new MissingFieldException("orderdate", "Orders");
        }
        if (order.getOrderstatus() == null || order.getOrderstatus().trim().isEmpty()) {
            throw new MissingFieldException("orderstatus", "Orders");
        }
        if (order.getItemid() == null || order.getItemid().trim().isEmpty()) {
            throw new MissingFieldException("itemid", "Orders");
        }
        if (order.getCustomerid() == null || order.getCustomerid().trim().isEmpty()) {
            throw new MissingFieldException("customerid", "Orders");
        }

        // 중복 데이터 최신 데이터 업데이트
        if (orderRepository.findById(order.getOrderid()).isPresent()) {
            logger.warn("중복 주문 ID 발견: {}", order.getOrderid());
            Orders existingOrder = orderRepository.findById(order.getOrderid()).get();
            
            // changedate 비교하여 최신 데이터만 업데이트
            if (isNewerData(order.getChangedate(), existingOrder.getChangedate())) {
                logger.info("더 최신 데이터로 업데이트: {} (기존: {}, 새로운: {})", 
                    order.getOrderid(), existingOrder.getChangedate(), order.getChangedate());
                existingOrder.setOrderdate(order.getOrderdate());
                existingOrder.setOrderstatus(order.getOrderstatus());
                existingOrder.setItemid(order.getItemid());
                existingOrder.setCustomerid(order.getCustomerid());
                existingOrder.setTotalamount(order.getTotalamount());
                existingOrder.setChangedate(order.getChangedate());
                orderRepository.save(existingOrder);
            } else {
                logger.info("기존 데이터가 더 최신이므로 업데이트 건너뜀: {} (기존: {}, 새로운: {})", 
                    order.getOrderid(), existingOrder.getChangedate(), order.getChangedate());
            }
        }

        logger.debug("주문 데이터 검증 완료: {}", order.getOrderid());
    }

    /**
     * 두 changedate 문자열을 비교하여 첫 번째가 더 최신인지 확인
     * ISO 8601 형식 (예: "2025-01-01T10:00:00")을 파싱하여 비교
     */
    private boolean isNewerData(String newChangedate, String existingChangedate) {
        try {
            // null 체크
            if (newChangedate == null || existingChangedate == null) {
                logger.warn("changedate가 null입니다. new: {}, existing: {}", newChangedate, existingChangedate);
                return false;
            }
            
            // ISO 8601 형식 파싱
            java.time.LocalDateTime newDateTime = java.time.LocalDateTime.parse(newChangedate);
            java.time.LocalDateTime existingDateTime = java.time.LocalDateTime.parse(existingChangedate);
            
            // 새로운 데이터가 더 최신인지 확인
            return newDateTime.isAfter(existingDateTime);
            
        } catch (Exception e) {
            logger.error("changedate 비교 중 오류 발생: new={}, existing={}, error={}", 
                newChangedate, existingChangedate, e.getMessage());
            return false; // 오류 발생 시 업데이트하지 않음
        }
    }

}
