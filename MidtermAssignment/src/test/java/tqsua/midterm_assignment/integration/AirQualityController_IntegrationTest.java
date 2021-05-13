package tqsua.midterm_assignment.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqsua.midterm_assignment.MidtermAssignmentApplication;
import tqsua.midterm_assignment.cache.Cache;
import tqsua.midterm_assignment.model.AirQuality;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MidtermAssignmentApplication.class)
@AutoConfigureMockMvc
class AirQualityController_IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private Cache cache;

    @AfterEach
    public void tearDown() {
        cache.clean();
    }

    @Test
    void givenAirQuality_whenGetAirQuality_thenReturnAirQualityObject() throws Exception {
        AirQuality aq = new AirQuality(30, 40, 41, "p1", "p2");
        cache.setAirQuality("USA", "Alaska", "City1", aq);

        mvc.perform(get("/api/airquality?country=USA&state=Alaska&city=City1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.humidity", is(30)))
                .andExpect(jsonPath("$.aqius", is(40)))
                .andExpect(jsonPath("$.aqicn", is(41)))
                .andExpect(jsonPath("$.mainus", is("p1")))
                .andExpect(jsonPath("$.maincn", is("p2")));
    }

    @Test
    void whenGetNonexistentAirQuality_thenReturnNotFound() throws Exception{
        mvc.perform(get("/api/airquality?country=A&state=B&city=C").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void whenGetStatistics_thenReturnStatistics() throws Exception {
        cache.setCount(3);
        cache.setHits(1);
        cache.setMisses(2);

        mvc.perform(get("/api/statistics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(3)))
                .andExpect(jsonPath("$.hits", is(1)))
                .andExpect(jsonPath("$.misses", is(2)));
    }
}