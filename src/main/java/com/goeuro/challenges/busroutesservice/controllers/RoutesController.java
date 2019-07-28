package com.goeuro.challenges.busroutesservice.controllers;

import com.goeuro.challenges.busroutesservice.dto.RouteResponse;
import com.goeuro.challenges.busroutesservice.services.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoutesController {

  private static final Logger LOG = LoggerFactory.getLogger(RoutesController.class);

  private final RouteService routeService;

  @Autowired
  public RoutesController(RouteService routeService) {
    this.routeService = routeService;
  }

  @GetMapping("direct")
  public ResponseEntity<RouteResponse> findDirectRoute(
      @RequestParam("dep_sid") Integer departureId,
      @RequestParam("arr_sid") Integer arrivalId) {

    LOG.debug("Started processing request. departureId: {}, arrivalId: {}",
        departureId, arrivalId);

    boolean directRouteExists = routeService.hasDirectRoute(departureId, arrivalId);

    final RouteResponse response = new RouteResponse();
    response.setDepartureId(departureId);
    response.setArrivalId(arrivalId);
    response.setHasDirectRoute(directRouteExists);

    LOG.debug("Finished processing request");
    return ResponseEntity.ok(response);
  }

}
