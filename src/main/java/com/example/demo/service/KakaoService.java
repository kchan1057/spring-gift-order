package com.example.demo.service;

import com.example.demo.config.KakaoProperties;
import com.example.demo.dto.kakao.KakaoTokenResponseDto;
import io.netty.handler.codec.http.HttpHeaderValues;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoService {
  private final KakaoProperties kakaoProperties;

  private static final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";

  public KakaoService(KakaoProperties kakaoProperties) {
    this.kakaoProperties = kakaoProperties;
  }

  public String getAccessTokenFromKakao(String code){
    KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
        .uri(uriBuilder -> uriBuilder
        .scheme("https")
        .path("/oauth/token")
        .queryParam("grant_type", "authorization_code")
        .queryParam("client_id", kakaoProperties.getClientId())
        .queryParam("redirect_uri", kakaoProperties.getRedirectUrl())
        .queryParam("code", code)
        .build(true))
    .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
        .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> (Mono.error(new RuntimeException("Internal Server Error"))))
        .bodyToMono(KakaoTokenResponseDto.class)
        .block();

    //System.out.println("Access Token: " + kakaoTokenResponseDto.getAccessToken());
    //System.out.println("Refresh Token: " + kakaoTokenResponseDto.getRefreshToken());
    return kakaoTokenResponseDto.getAccessToken();
  }
}
