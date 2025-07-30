package com.example.demo.controller;

import com.example.demo.dto.kakao.KakaoTokenResponseDto;
import com.example.demo.dto.kakao.KakaoUserInfoDto;
import com.example.demo.entity.User;
import com.example.demo.jwt.Jwt;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.client.KakaoClient;
import com.example.demo.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class KakaoLoginController {

  private final KakaoClient kakaoClient;
  private final UserService userService;
  private final JwtProvider jwtProvider;

  public KakaoLoginController(KakaoClient kakaoClient, UserService userService,
      JwtProvider jwtProvider) {
    this.kakaoClient = kakaoClient;
    this.userService = userService;
    this.jwtProvider = jwtProvider;
  }

  @GetMapping("/callback")
  public ResponseEntity<Jwt> callback(@RequestParam("code") String code){
    //System.out.println("카카오 인가 코드: " + code);
    KakaoTokenResponseDto kakaoTokenResponseDto = kakaoClient.getAccessTokenFromKakao(code);
    String accessToken = kakaoTokenResponseDto.getAccessToken();

    System.out.println("카카오 access token: " + accessToken);

    KakaoUserInfoDto kakaoUserInfoDto = kakaoClient.getUserInfo(accessToken);
    Long kakaoId = kakaoUserInfoDto.id();

    User user = userService.findOrCreateByKakaoId(kakaoId);
    Jwt jwt = jwtProvider.createJwt(user.getId(), user.getRole());

    return new ResponseEntity<>(jwt, HttpStatus.OK);
  }
}
