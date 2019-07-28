package com.goeuro.challenges.busroutesservice.services;

public interface RouteService {

  boolean hasDirectRoute(Integer departureId, Integer arrivalId);

}
