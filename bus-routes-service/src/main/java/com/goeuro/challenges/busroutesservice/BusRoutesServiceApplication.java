package com.goeuro.challenges.busroutesservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusRoutesServiceApplication {

  private static final Logger LOG = LoggerFactory.getLogger(BusRoutesServiceApplication.class);

  public static void main(String[] args) {
    validateArguments(args);
    SpringApplication.run(BusRoutesServiceApplication.class, args);
  }

  private static void validateArguments(String[] args) {
    if (args.length == 0) {
      LOG.error("args: data file path is missed");
      throw new IllegalStateException("args: data file path is missed");
    }
  }

}
