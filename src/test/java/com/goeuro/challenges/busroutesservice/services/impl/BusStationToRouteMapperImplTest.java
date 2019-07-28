package com.goeuro.challenges.busroutesservice.services.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class BusStationToRouteMapperImplTest {

  private BusStationToRouteMapperImpl unit = new BusStationToRouteMapperImpl();

  @Test
  public void testMapData() {
    final Map<Integer, List<Integer>> inputData =
        ImmutableMap.<Integer, List<Integer>>builder()
            .put(0, ImmutableList.of(0, 1, 2, 3, 4))
            .put(1, ImmutableList.of(3, 1, 6, 5))
            .put(2, ImmutableList.of(0, 6, 4))
            .build();

    final ImmutableMap<Integer, Set<Integer>> expectedResult =
        ImmutableMap.<Integer, Set<Integer>>builder()
            .put(0, ImmutableSet.of(0, 2))
            .put(1, ImmutableSet.of(0, 1))
            .put(2, ImmutableSet.of(0))
            .put(3, ImmutableSet.of(0, 1))
            .put(4, ImmutableSet.of(0, 2))
            .put(5, ImmutableSet.of(1))
            .put(6, ImmutableSet.of(1, 2))
            .build();

    Assert.assertEquals(expectedResult, unit.mapData(inputData));
  }

}