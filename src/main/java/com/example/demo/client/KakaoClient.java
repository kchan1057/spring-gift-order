package com.example.demo.client;

import com.example.demo.config.KakaoProperties;
import com.example.demo.dto.kakao.KakaoObjectTemplateDto;
import com.example.demo.dto.kakao.KakaoTokenResponseDto;
import com.example.demo.dto.kakao.KakaoUserInfoDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoClient {
  private final KakaoProperties kakaoProperties;
  private final RestClient restClient;
  private final ObjectMapper objectMapper;

  private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
  private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
  private static final String KAKAO_SEND_MESSAGE_TO_ME = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

  public KakaoClient(KakaoProperties kakaoProperties, RestClient restClient,
      ObjectMapper objectMapper) {
    this.kakaoProperties = kakaoProperties;
    this.restClient = restClient;
    this.objectMapper = objectMapper;
  }

  public KakaoTokenResponseDto getAccessTokenFromKakao(String code) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", kakaoProperties.getClientId());
    body.add("redirect_url", kakaoProperties.getRedirectUrl());
    body.add("code", code);

    return restClient.post()
                     .uri(KAKAO_TOKEN_URL)
                     .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                     .body(body)
                     .retrieve()
                     .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                       throw new RuntimeException("카카오 요청 실패 (4xx): " + res.getStatusCode());
                     })
                     .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                       throw new RuntimeException("카카오 서버 오류 (5xx): " + res.getStatusCode());
                     })
                     .body(KakaoTokenResponseDto.class);
  }

  public KakaoUserInfoDto getUserInfo(String accessToken) {
    return restClient.post()
                     .uri(KAKAO_USER_INFO_URL)
                     .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                     .retrieve()
                     .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                       throw new RuntimeException("카카오 요청 실패: " + res.getStatusCode());
                     })
                     .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                       throw new RuntimeException("카카오 서버 오류: " + res.getStatusCode());
                     })
                     .body(KakaoUserInfoDto.class);
  }

  public ResponseEntity<Integer> sendKakaoMessage(String accessToken, KakaoObjectTemplateDto dto){
    try{
      String messageJson = objectMapper.writeValueAsString(dto);
      MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
      formData.add("template_object", messageJson);

      String response = restClient.post()
          .uri(KAKAO_SEND_MESSAGE_TO_ME)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
          .body(formData)
          .retrieve()
          .body(String.class);

      JsonNode root = objectMapper.readTree(response);
      int resultCode = root.path("result_code").asInt();

      if (resultCode != 0) {
        throw new RuntimeException("카카오 메시지 전송 실패: result_code = " + resultCode);
      }

      return ResponseEntity.ok(resultCode);
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
