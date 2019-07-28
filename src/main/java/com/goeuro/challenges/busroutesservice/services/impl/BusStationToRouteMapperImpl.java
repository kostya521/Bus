package com.goeuro.challenges.busroutesservice.services.impl;

import com.goeuro.challenges.busroutesservice.services.DataMapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * This class is used to map stations to routes.
 */
@Component
public class BusStationToRouteMapperImpl implements
    DataMapper<Map<Integer, List<Integer>>, Map<Integer, Set<Integer>>> {

  /**
   * This method converts maping from key:routeId, value=[stationId, ...] to key:stationId,
   * value=[routeId,...]
   *
   * @param inputData a Map where key is a route id and value is a List of station ids
   * @return a Map where key is a station and value is a Set of route ids
   */
  @Override
  public Map<Integer, Set<Integer>> mapData(Map<Integer, List<Integer>> inputData) {
    final Map<Integer, Set<Integer>> stationToRouteMapping = new HashMap<>();
    inputData.forEach((routeId, stations) ->
        mapStationToRoute(stationToRouteMapping, routeId, stations));

    return stationToRouteMapping;
  }

  private void mapStationToRoute(Map<Integer, Set<Integer>> stationToRouteMap, Integer routeId,
      List<Integer> stations) {

    for (final Integer stationId : stations) {
      if (stationToRouteMap.containsKey(stationId)) {
        stationToRouteMap.get(stationId).add(routeId);
      } else {
        final Set<Integer> routes = new HashSet<>();
        routes.add(routeId);
        stationToRouteMap.put(stationId, routes);
      }
    }
  }


}
