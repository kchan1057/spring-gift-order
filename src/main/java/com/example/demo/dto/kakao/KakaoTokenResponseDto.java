package com.example.demo.dto.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoTokenResponseDto {

  @JsonProperty("token_type")
  public String tokenType;

  @JsonProperty("access_type")
  public String accessToken;

  @JsonProperty("id_token")
  public String idToken;

  @JsonProperty("expires_in")
  public Integer expiresIn;

  @JsonProperty("refresh_token")
  public String refreshToken;

  @JsonProperty("refresh_token_expires_in")
  public Integer refreshTokenExpiresIn;

  @JsonProperty("scope")
  public String scope;

  public KakaoTokenResponseDto(String tokenType, String accessToken, String idToken, Integer expiresIn, String refreshToken, Integer refreshTokenExpiresIn, String scope){
    this.tokenType = tokenType;
    this.accessToken = accessToken;
    this.idToken = idToken;
    this.expiresIn = expiresIn;
    this.refreshToken = refreshToken;
    this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    this.scope = scope;
  }
  public String getTokenType(){
    return tokenType;
  }

  public String getAccessToken(){
    return accessToken;
  }

  public String getIdToken(){
    return idToken;
  }

  public Integer getExpiresIn(){
    return expiresIn;
  }

  public String getRefreshToken(){
    return refreshToken;
  }

  public Integer getRefreshTokenExpiresIn(){
    return refreshTokenExpiresIn;
  }

  public String getScope(){
    return scope;
  }
}
