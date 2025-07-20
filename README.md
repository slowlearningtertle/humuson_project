# 휴머스온 과제 테스트 - 주문 데이터 연동 인터페이스


## 목차
- [프로젝트 개요](#-프로젝트-개요)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [실행 방법](#-실행-방법)
- [API 엔드포인트](#-api-엔드포인트)
- [주요 클래스 설명](#-주요-클래스-설명)
- [클래스 다이어그램](#-클래스-다이어그램)
- [외부 시스템 연동 동작 확인](#-외부-시스템-연동-동작-확인)
- [예외 처리 전략](#-예외-처리-전략)
- [웹 인터페이스](#-웹-인터페이스)

##  프로젝트 개요

이 프로젝트는 외부 시스템과의 주문 데이터 연동을 위한 인터페이스를 구현한 Spring Boot 기반의 주문 관리 시스템입니다. 외부 시스템에서 JSON 형식의 주문 데이터를 받아와 내부 시스템에 저장하고, 내부 데이터를 외부로 전송하는 기능을 제공합니다.

##  주요 기능

### 1. 주문 관리 시스템
- 외부 시스템으로부터 주문 데이터 수신 및 저장
- 주문 ID, 고객 ID, 주문 날짜, 주문 상태, 상품 ID, 총 금액, 수정 시간 등 관리
- 주문 상태: 대기중, 배송중, 배송완료 등

### 2. 외부 시스템 데이터 연동
- **데이터 수신**: 외부 시스템에서 JSON 형식의 주문 데이터를 HTTP POST로 수신
- **데이터 전송**: 내부 시스템의 주문 데이터를 JSON 형식으로 외부 시스템에 전송
- **RESTful API**: `/api/fetch-data`, `/api/send-data` 엔드포인트 제공

### 3. 데이터 저장 및 조회
- **메모리 저장**: In-Memory Repository를 통한 주문 데이터 저장
- **주문 조회**: 주문 ID를 통한 개별 조회 및 전체 주문 목록 조회
- **데이터 검증**: 필수 필드 검증 및 중복 데이터 처리

### 4. 예외 처리
- **HTTP 통신 예외**: http 상태 코드 처리
- **데이터 검증 예외**: 필수 필드 누락, 잘못된 데이터 형식 처리
- **동기화 예외**: 데이터 동기화 실패, 중복 데이터 처리
- **전역 예외 처리**: 통합된 예외 응답 형식 제공

## 기술 스택

- **Java 21**
- **Spring Boot 3.4.8-SNAPSHOT**
- **Spring Web**: RESTful API 구현
- **Spring Data JPA**: 데이터 접근 계층
- **H2 Database**: 인메모리 데이터베이스
- **Thymeleaf**: 웹 템플릿 엔진 (주문 데이터 확인용)
- **Lombok**: 보일러플레이트 코드 감소
- **Gradle**: 빌드 도구

##  프로젝트 구조

```
info
├── class-diagram                 # 클래스 다이어그램 그림
└── external-working              # 외부 서버와 데이터 송수신한 그림 자료
src/TechAssignmentProject/
├── src/main/java/com/humuson/TechAssignmentProject/
│   ├── Common/                   # 공통 컴포넌트
│   │   ├── Controller/           # 공통 컨트롤러
│   │   ├── DAO/                  # 데이터 액세스 객체
│   │   └── Repository/           # 리포지토리 인터페이스
│   ├── DataIntergration/         # 데이터 연동 모듈
│   │   ├── Controller/           # 외부 연동 컨트롤러
│   │   ├── Service/              # 외부 연동 서비스
│   │   └── Repository/           # 외부 연동 리포지토리
│   ├── Exception/                # 예외 처리 모듈
│   │   ├── base/                 # 기본 예외 클래스
│   │   ├── data/                 # 데이터 관련 예외
│   │   ├── http/                 # HTTP 통신 예외
│   │   ├── sync/                 # 동기화 예외
│   │   ├── handler/              # 예외 핸들러
│   │   └── util/                 # 예외 처리 유틸리티
│   ├── Order/                    # 주문 관리 모듈
│   │   ├── Controller/           # 주문 컨트롤러
│   │   ├── Service/              # 주문 서비스
│   │   └── Repository/           # 주문 리포지토리
│   └── TechAssignmentProjectApplication.java
├── src/main/resources/
│   ├── application.properties    # 애플리케이션 설정
│   └── templates/                # 웹 템플릿
└── src/test/                     # 테스트 코드
```


### 사전 요구사항
- Java 21 이상
- Gradle 7.0 이상

### 실행 방법
- TechAssignmentProjectApplication.java 파일을 실행

### 실행 후 웹 접속
- 메인 페이지: http://localhost:8080

##  API 엔드포인트

### 외부 시스템 연동 API
- `GET /api/test-connection`: 연결 테스트 (확인용)
- `GET /api/send-data`: 내부 데이터를 외부 시스템으로 전송
- `POST /api/fetch-data`: 외부 시스템에서 데이터 수신

### 주문 관리 API
- `GET /order`: 주문 목록 조회 (웹 페이지)
- `POST /order`: 새 주문 생성
- `PUT /order/{orderId}`: 주문 상태 업데이트
- `DELETE /order/{orderId}`: 주문 삭제

##  주요 클래스 설명

### 1. Orders (DAO)
주문 데이터를 표현하는 엔티티 클래스
- `orderid`: 주문 ID (고유 식별자)
- `orderdate`: 주문 날짜
- `orderstatus`: 주문 상태
- `itemid`: 상품 ID
- `customerid`: 고객 ID
- `totalamount`: 총 금액
- `changedate`: 주문 데이터 수정 시간

### 2. ExternalService
외부 시스템과의 데이터 연동을 담당하는 서비스 클래스
- `fetchData()`: 외부 시스템에서 데이터 수신 및 저장
- `sendData()`: 내부 데이터를 외부 시스템으로 전송
- `validateOrder()`: 주문 데이터 검증

### 3. InMemoryOrderRepository
메모리 기반 주문 데이터 저장소
- HashMap을 사용한 인메모리 데이터 저장
- 초기 테스트 데이터 포함
- CRUD 작업 지원

### 4. GlobalExceptionHandler
전역 예외 처리 핸들러
- HTTP 통신, 데이터 검증, 동기화 예외 처리
- 일관된 예외 응답 형식 제공

##  클래스 다이어그램

프로젝트의 클래스 다이어그램은 다음과 같습니다:

![클래스 다이어그램](info/class-diagram/클래스%20다이어그램.jpg)

이 다이어그램은 외부 시스템과의 데이터 연동을 담당하는 주요 클래스들과 이들 간의 관계를 보여줍니다.

##  외부 시스템 연동 동작 확인

### 1. POST 요청으로 데이터 전송
![POST 요청으로 데이터 전송](info/external-working/포스트로%20보내기.png)

### 2. 연동 결과 확인
![연동 결과 확인](info/external-working/연동%20된%20것%20확인하기.png)

##  예외 처리 전략

### 1. HTTP 통신 예외
- `HttpCommunicationException`: 연결 실패, 타임아웃
- `ExternalApiException`: 외부 API 오류
- `ApiTimeoutException`: API 응답 타임아웃

### 2. 데이터 검증 예외
- `DataValidationException`: 데이터 형식 오류
- `MissingFieldException`: 필수 필드 누락
- `JsonParsingException`: JSON 파싱 오류

### 3. 동기화 예외
- `DataSyncException`: 데이터 동기화 실패
- `DuplicateDataException`: 중복 데이터 처리

##  웹 인터페이스

### 메인 페이지 (`/`)
- 주문 목록 표시
- 새 주문 추가 폼
- 주문 취소 기능

### 주문 관리 페이지 (`/order`)
- 전체 주문 목록 조회
- 주문 상태 관리
- 주문 CRUD 작업

