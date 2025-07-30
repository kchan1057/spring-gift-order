package com.example.demo.config;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  private static final int CONNECT_TIMEOUT_SECONDS = 3;
  private static final int READ_TIMEOUT_SECONDS = 3;

  @Bean
  public RestClient restClient(){
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS));
    requestFactory.setConnectTimeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS));

    return RestClient.builder()
        .requestFactory(requestFactory)
        .build();
  }
}
