package com.goeuro.challenges.busroutesservice.services.impl;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RouteServiceImplTest {

  @Mock
  private Map<Integer, Set<Integer>> mappings;
  @InjectMocks
  private RouteServiceImpl unit;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    Mockito.when(mappings.getOrDefault(1, Collections.emptySet()))
        .thenReturn(ImmutableSet.of(1, 2, 3, 4));
    Mockito.when(mappings.getOrDefault(2, Collections.emptySet()))
        .thenReturn(ImmutableSet.of(3, 4, 5, 6));
    Mockito.when(mappings.getOrDefault(3, Collections.emptySet()))
        .thenReturn(Collections.emptySet());
  }

  @Test
  public void testIsDirectRouteExistReturnsTrue() {
    Assert.assertTrue(unit.hasDirectRoute(1, 2));

    Mockito.verify(mappings, Mockito.times(1)).getOrDefault(1, Collections.emptySet());
    Mockito.verify(mappings, Mockito.times(1)).getOrDefault(2, Collections.emptySet());
    Mockito.verifyNoMoreInteractions(mappings);
  }

  @Test
  public void testIsDirectRouteExistReturnsFalse() {
    Assert.assertFalse(unit.hasDirectRoute(2, 3));

    Mockito.verify(mappings, Mockito.times(1)).getOrDefault(2, Collections.emptySet());
    Mockito.verify(mappings, Mockito.times(1)).getOrDefault(3, Collections.emptySet());
    Mockito.verifyNoMoreInteractions(mappings);
  }

}