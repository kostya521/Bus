package com.goeuro.challenges.busroutesservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteResponse {

  @JsonProperty(value = "dep_sid")
  private Integer departureId;

  @JsonProperty(value = "arr_sid")
  private Integer arrivalId;

  @JsonProperty(value = "direct_bus_route")
  private Boolean hasDirectRoute;

  public Integer getDepartureId() {
    return departureId;
  }

  public void setDepartureId(Integer departureId) {
    this.departureId = departureId;
  }

  public Integer getArrivalId() {
    return arrivalId;
  }

  public void setArrivalId(Integer arrivalId) {
    this.arrivalId = arrivalId;
  }

  public Boolean getHasDirectRoute() {
    return hasDirectRoute;
  }

  public void setHasDirectRoute(Boolean hasDirectRoute) {
    this.hasDirectRoute = hasDirectRoute;
  }

}
