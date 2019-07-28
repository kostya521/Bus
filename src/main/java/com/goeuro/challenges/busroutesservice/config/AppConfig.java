package com.goeuro.challenges.busroutesservice.config;

import com.goeuro.challenges.busroutesservice.services.DataFileParser;
import com.goeuro.challenges.busroutesservice.services.DataMapper;
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


  /**
   * Map contains parsed data from data file
   */
  @Bean
  Map<Integer, List<Integer>> routeToStationMap(DataFileParser dataFileParser) {
    final List<String> nonOptionArgs = args.getNonOptionArgs();
    Assert.notEmpty(nonOptionArgs, () -> "args: data file path is missed");

    return dataFileParser.processDataFile(nonOptionArgs.get(0));
  }

  /**
   * Map which contains mapping of stations to routes.
   */
  @Bean("stationsToRouteMap")
  Map<Integer, Set<Integer>> stationsToRouteMap(Map<Integer, List<Integer>> routeToStationMap,
      DataMapper<Map<Integer, List<Integer>>, Map<Integer, Set<Integer>>> dataMapper) {
    return dataMapper.mapData(routeToStationMap);
  }

}
