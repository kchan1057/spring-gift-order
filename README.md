- #  spring-product-api

스프링 부트를 활용한 REST API 프로젝트입니다.
<br><br>
---

## 추가 구현 기능(07.30)
- [X] 주문할 때 수령인에게 보낼 메시지를 작성할 수 있다.
- [X] 상품 옵션과 해당 수량을 선택하여 주문하면 해당 상품 옵션의 수량이 차감된다.
- [X] 해당 상품이 위시 리스트에 있는 경우 위시 리스트에서 삭제한다.
- [X] 나에게 보내기를 읽고 주문 내역을 카카오톡 메시지로 전송한다.
- [X] 메시지는 메시지 템플릿의 기본 템플릿이나 사용자 정의 템플릿을 사용하여 자유롭게 작성한다. (Feed 템플릿 이용.)
---

###  상품 목록 조회

- **URL**: `GET /products`
- **설명**: 등록된 모든 상품 목록을 조회합니다.
---

###  상품 단건 조회

- **URL**: `GET /products/{id}`
- **설명**: ID에 해당하는 상품 정보를 조회합니다.
---

###  상품 추가

- **URL**: `POST /products`
- **설명**: 새로운 상품을 등록합니다.
- **요청 바디 예시**:
```json
{
  "name": "초코 케이크",
  "price": 5000,
  "imageUrl": "https://example.com/choco.jpg"
}
```
###  상품 삭제

- **URL**: `DELETE /products/{id}`
- **설명**: 지정한 ID의 상품을 삭제합니다.
---

## 관리자 페이지(Thymeleaf 기반)

### 상품 목록 (홈 화면)

- **URL**: GET /product-page
- **설명**: 관리자용 상품 리스트 페이지(HTML 기반)
---

### 상품 등록 폼

- **URL**: GET /product-page/new
- **설명**: 새로운 상품을 등록하는 폼 페이지
---

### 상품 수정 폼

- **URL**: GET /product-page/{id}
- **설명**: 기존 상품 정보를 수정하는 폼 페이지
---

### 상품 삭제 요청

- **URL**: POST /product-page/{id}/delete
- **설명**: HTML 페이지에서 상품 삭제 요청을 전송합니다

### 기술 스택
Java 21

Spring Boot 3.5.3

Spring Web (REST API)

Spring JPA

Thymeleaf (관리자 페이지용)

H2 Database (in-memory)

JUnit5 (E2E 테스트 코드 작성)

Jwt(Spring Security 사용 X)

