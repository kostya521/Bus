package com.goeuro.challenges.busroutesservice.services.impl;

import com.goeuro.challenges.busroutesservice.services.RouteService;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service to verify if two given stations has a direct route.
 */
@Service
public class RouteServiceImpl implements RouteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);

  private final Map<Integer, Set<Integer>> stationsToRouteMap;

  @Autowired
  public RouteServiceImpl(
      @Qualifier("stationsToRouteMap") Map<Integer, Set<Integer>> stationsToRouteMap) {
    this.stationsToRouteMap = stationsToRouteMap;
  }

  /**
   * Verify if two given stations has a direct route.
   *
   * @param departureId an Integer representing departure station id
   * @param arrivalId an Integer representing arrival station id
   * @return <b>true</b> in case at least one route is found otherwise returns <b>false</b>
   */
  @Override
  public boolean hasDirectRoute(Integer departureId, Integer arrivalId) {
    final Collection<Integer> foundRoutes = findRoutesByStations(departureId, arrivalId);
    if (foundRoutes.isEmpty()) {
      LOGGER.debug("Routes containing both {} and {} station not found", departureId, arrivalId);
      return false;
    } else {
      LOGGER.debug("Found routes containing both {} and {} stations: {}",
          departureId, arrivalId, foundRoutes);
      return true;
    }
  }

  private Collection<Integer> findRoutesByStations(Integer departureId, Integer arrivalId) {
    final Collection<Integer> routesWithDepartureId = stationsToRouteMap
        .getOrDefault(departureId, Collections.emptySet());
    final Collection<Integer> routesWithArrivalId = stationsToRouteMap
        .getOrDefault(arrivalId, Collections.emptySet());

    final HashSet<Integer> result = new HashSet<>(routesWithDepartureId);
    result.retainAll(routesWithArrivalId);
    return result;
  }

}
