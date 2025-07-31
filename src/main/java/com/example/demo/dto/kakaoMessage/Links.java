package com.example.demo.dto.kakaoMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {

  @JsonProperty("web_url")
  private String webUrl;

  @JsonProperty("mobile_web_url")
  private String mobileWebUrl;

  public String getMobileWebUrl() {
    return mobileWebUrl;
  }

  public Links(String mobileWebUrl, String webUrl) {
    this.mobileWebUrl = mobileWebUrl;
    this.webUrl = webUrl;
  }

  public void setMobileWebUrl(String mobileWebUrl) {
    this.mobileWebUrl = mobileWebUrl;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }
}
