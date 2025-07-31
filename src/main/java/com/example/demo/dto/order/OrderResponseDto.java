package com.example.demo.dto.order;

import java.time.LocalDateTime;

public record OrderResponseDto(
    Long orderId,
    Long optionId,
    int quantity,
    LocalDateTime orderDateTime,
    String message
) {
}
