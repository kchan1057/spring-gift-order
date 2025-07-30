package com.example.demo.entity.kakaoMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contents {

  @JsonProperty("title")
  private String title;

  @JsonProperty("image_url")
  private String imageUrl;

  @JsonProperty("description")
  private String description;

  @JsonProperty("link")
  private Links link;

  public Contents(String imageUrl, String title, String description, Links link) {
    this.imageUrl = imageUrl;
    this.title = title;
    this.description = description;
    this.link = link;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Links getLink() {
    return link;
  }

  public void setLink(Links link) {
    this.link = link;
  }
}
