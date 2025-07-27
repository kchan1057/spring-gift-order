package com.example.demo.controller;

import com.example.demo.service.KakaoService;
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

  public KakaoLoginController(KakaoService kakaoService) {
    this.kakaoService = kakaoService;
  }

  @GetMapping("/callback")
  public ResponseEntity<?> callback(@RequestParam("code") String code){
    //System.out.println("카카오 인가 코드: " + code);
    String accessToken = kakaoService.getAccessTokenFromKakao(code);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
