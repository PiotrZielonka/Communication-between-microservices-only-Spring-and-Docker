package com.task.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class CreditApplication {

  public static void main(String[] args) {
    SpringApplication.run(CreditApplication.class, args);
  }

}
