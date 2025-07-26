#  spring-product-api

스프링 부트를 활용한 **위시리스트(WishList)** 관리 REST API 프로젝트입니다.
<br><br>
---

## 추가 구현 기능(07.24)
- 카카오 로그인 구현
- [X] 카카오 API를 사용하기 위한 애플리케이션을 등록.
- [X] 카카오계정 로그인을 통해 인증 코드 받기. -> 이를 Controller에서 RequestParam으로 받은 후 getAccessToken 메소드의 매개변수로 삽입.
- [X] 토큰 받기를 읽고 액세스 토큰을 추출하기. -> Service-Layer에서 getAccessToken을 실행 후 액세스 토큰과 리프레쉬 토큰 발급.
- [X] 앱 키, 인가 코드가 유출 방지 고려. -> .gitignore로 관리
- [X] (선택)  인가 코드를 받는 방법이 불편한 경우 카카오 로그인 화면을 구현. -> 기존의 로그인 html에 카카오 로그인 버튼을 넣어서 작동하게끔 유도.
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

