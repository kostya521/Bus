package com.goeuro.challenges.busroutesservice.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.goeuro.challenges.busroutesservice.services.RouteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(RoutesController.class)
public class RoutesControllerTest {

  private static final String SERVICE_URL = "/api/direct";
  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @MockBean
  private RouteService routeService;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testDirectRouteExists() throws Exception {
    Mockito.when(routeService.hasDirectRoute(1, 2)).thenReturn(true);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("dep_sid", "1");
    params.add("arr_sid", "2");

    mockMvc.perform(get(SERVICE_URL).params(params)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.dep_sid", is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.arr_sid", is(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.direct_bus_route", is(true)))
        .andReturn();
  }

  @Test
  public void testDirectRouteDoesNotExist() throws Exception {
    Mockito.when(routeService.hasDirectRoute(3, 4)).thenReturn(false);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("dep_sid", "3");
    params.add("arr_sid", "4");

    mockMvc.perform(get(SERVICE_URL).params(params)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.dep_sid", is(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.arr_sid", is(4)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.direct_bus_route", is(false)))
        .andReturn();
  }

}