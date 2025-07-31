package com.example.demo.controller;


import com.example.demo.dto.order.OrderRequestDto;
import com.example.demo.dto.order.OrderResponseDto;
import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
import com.example.demo.validation.LoginMember;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/api/orders")
  public ResponseEntity<OrderResponseDto> createOrder(
      @RequestBody OrderRequestDto dto,
      @LoginMember User user,
      @RequestHeader("X-Kakao-Access-Token") String kakaoAccessToken) {
    OrderResponseDto response = orderService.placeOrder(user, dto, kakaoAccessToken);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
