package com.goeuro.challenges.busroutesservice.config;

import com.goeuro.challenges.busroutesservice.services.DataFileParser;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
public class AppConfig {

  @Autowired
  private ApplicationArguments args;

  @Bean
  Map<Integer, Set<Integer>> stationsToRouteMapping(DataFileParser dataFileParserService) {
    final List<String> nonOptionArgs = args.getNonOptionArgs();
    Assert.notEmpty(nonOptionArgs, () -> "args: data file path is missed");

    return dataFileParserService.processDataFile(nonOptionArgs.get(0));
  }

}
