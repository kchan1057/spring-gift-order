package com.example.demo.service;


import com.example.demo.client.KakaoClient;
import com.example.demo.dto.kakao.KakaoObjectTemplateDto;
import com.example.demo.dto.order.OrderRequestDto;
import com.example.demo.dto.order.OrderResponseDto;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOption;
import com.example.demo.entity.User;
import com.example.demo.entity.kakaoMessage.Contents;
import com.example.demo.entity.kakaoMessage.Links;
import com.example.demo.exception.OptionNotFoundException;
import com.example.demo.repository.OptionRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.WishRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OptionRepository optionRepository;
  private final WishRepository wishRepository;
  private final OrderRepository orderRepository;
  private final KakaoClient kakaoClient;

  public OrderService(OptionRepository optionRepository, WishRepository wishRepository,
      OrderRepository orderRepository, KakaoClient kakaoClient) {
    this.optionRepository = optionRepository;
    this.wishRepository = wishRepository;
    this.orderRepository = orderRepository;
    this.kakaoClient = kakaoClient;
  }

  public OrderResponseDto placeOrder(User user, OrderRequestDto dto, String kakaoAccessToken){
    ProductOption productOption = optionRepository.findById(dto.optionId())
        .orElseThrow(() -> new OptionNotFoundException("해당 옵션 없음."));

    productOption.subtract(dto.quantity());
    optionRepository.save(productOption);

    Product wishProduct = productOption.getProduct();
    Long productId = wishProduct.getId();

    wishRepository.deleteById_UserIdAndId_ProductId(user.getId(), productId);

    Order order = new Order(user, productOption, dto.quantity(), dto.message());
    orderRepository.save(order);

    KakaoObjectTemplateDto templateDto = buildMessageTemplate(order);
    kakaoClient.sendKakaoMessage(kakaoAccessToken, templateDto);

    return new OrderResponseDto(
        order.getId(),
        productOption.getId(),
        order.getQuantity(),
        order.getCreatedAt(),
        order.getMessage()
    );
  }
  private KakaoObjectTemplateDto buildMessageTemplate(Order order) {
    return new KakaoObjectTemplateDto(
        "feed",
        new Contents(
            "[주문 확인] " + order.getOption().getProduct().getName(),
            "http://localhost:8080/images/feedImage.jpg",
            "수량: " + order.getQuantity() + "\n요청사항: " + order.getMessage(),
            new Links("https://www.naver.com",
                "https://m.naver.com")
        )
    );
  }
}
