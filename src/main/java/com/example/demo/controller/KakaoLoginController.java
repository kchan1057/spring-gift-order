package com.example.demo.controller;

import com.example.demo.dto.kakao.KakaoUserInfoDto;
import com.example.demo.entity.User;
import com.example.demo.jwt.Jwt;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.service.KakaoService;
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

  private final KakaoService kakaoService;
  private final UserService userService;
  private final JwtProvider jwtProvider;

  public KakaoLoginController(KakaoService kakaoService, UserService userService,
      JwtProvider jwtProvider) {
    this.kakaoService = kakaoService;
    this.userService = userService;
    this.jwtProvider = jwtProvider;
  }

  @GetMapping("/callback")
  public ResponseEntity<Jwt> callback(@RequestParam("code") String code){
    //System.out.println("카카오 인가 코드: " + code);
    String kakaoAccessToken = kakaoService.getAccessTokenFromKakao(code);
    KakaoUserInfoDto kakaoUserInfo = kakaoService.getUserInfo(kakaoAccessToken);
    Long kakaoId = kakaoUserInfo.id();

    User user = userService.findOrCreateByKakaoId(kakaoId);
    Jwt jwt = jwtProvider.createJwt(user.getId(), user.getRole());
    return new ResponseEntity<>(jwt, HttpStatus.OK);
  }
}
