package com.example.demo.dto.kakao;

import com.example.demo.dto.kakaoMessage.Contents;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class KakaoObjectTemplateDto {

  @JsonProperty("object_type")
  private String objectType;

  @JsonProperty("content")
  private Contents content;

  public KakaoObjectTemplateDto(String objectType, Contents content) {
    this.objectType = objectType;
    this.content = content;
  }

  public Contents getContent() {
    return content;
  }

  public void setContent(Contents content) {
    this.content = content;
  }

  public String getObjectType() {
    return objectType;
  }


}
