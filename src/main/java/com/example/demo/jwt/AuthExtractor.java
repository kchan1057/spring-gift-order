package com.example.demo.jwt;

import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.service.user.UserService;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;

public class AuthExtractor {

  public static User extractUserFromRequest(HttpServletRequest request, JwtProvider jwtProvider,
      UserService userService) {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException("AccessToken이 없습니다.");
    }

    String token = authHeader.replace("Bearer ", "").trim();
    Long userId = extractIdFromToken(token, jwtProvider);
    return userService.findById(userId);
  }

  private static Long extractIdFromToken(String token, JwtProvider jwtProvider) {
    try {
      return ((Number)jwtProvider.getClaims(token).get("userId")).longValue();
    } catch (Exception e) {
      throw new UnauthorizedException("AccessToken이 유효하지 않습니다.");
    }
  }
}
