package tqsua.midterm_assignment.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqsua.midterm_assignment.model.AirQuality;
import tqsua.midterm_assignment.service.AirQualityService;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AirQualityController.class)
class AirQualityController_UnitTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirQualityService service;

    @Test
    void givenAirQuality_whenGetAirQuality_thenReturnAirQualityObject() throws Exception {
        AirQuality aq = new AirQuality(30, 40, 41, "p1", "p1");

        given(service.getAirQualityByCity("USA", "Alaska", "City1")).willReturn(aq);

        mvc.perform(get("/api/airquality?country=USA&state=Alaska&city=City1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.humidity", is(30)))
                .andExpect(jsonPath("$.aqius", is(40)))
                .andExpect(jsonPath("$.aqicn", is(41)))
                .andExpect(jsonPath("$.mainus", is("p1")))
                .andExpect(jsonPath("$.maincn", is("p1")));
        verify(service, VerificationModeFactory.times(1)).getAirQualityByCity("USA", "Alaska", "City1");
    }

    @Test
    void whenGetNonexistentAirQuality_thenReturnNotFound() throws Exception{
        given(service.getAirQualityByCity("A", "B", "C")).willReturn(null);

        mvc.perform(get("/api/airquality?country=A&state=B&city=C").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
        verify(service, VerificationModeFactory.times(1)).getAirQualityByCity("A", "B", "C");
    }

    @Test
    void whenGetStatistics_thenReturnStatistics() throws Exception {
        HashMap<String, Integer> stats = new HashMap<>();
        stats.put("count", 3);
        stats.put("hits", 1);
        stats.put("misses", 2);
        given(service.getStatistics()).willReturn(stats);

        mvc.perform(get("/api/statistics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(3)))
                .andExpect(jsonPath("$.hits", is(1)))
                .andExpect(jsonPath("$.misses", is(2)));
        verify(service, VerificationModeFactory.times(1)).getStatistics();
    }
}
