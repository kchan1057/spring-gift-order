package com.example.demo.dto.order;

public record OrderRequestDto(
    Long optionId,
    int quantity,
    String message
) {
}
