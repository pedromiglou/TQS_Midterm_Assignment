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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MidtermAssignmentApplication.class)
@AutoConfigureMockMvc
class CityController_IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private Cache cache;

    @AfterEach
    public void tearDown() {
        cache.clean();
    }

    @Test
    void givenCountries_whenGetCountries_thenReturnJsonArray() throws Exception {
        String[] countries = new String[]{"USA", "France"};
        cache.setCountries(countries);

        mvc.perform(get("/api/countries").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("USA")));
    }

    @Test
    void givenStates_whenGetStates_thenReturnJsonArray() throws Exception {
        String[] states = new String[]{"Alaska", "Kansas"};
        cache.setStates("USA", states);

        mvc.perform(get("/api/states?country=USA").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("Alaska")));
    }

    @Test
    void whenGetStatesFromNonexistentCountry_thenReturnNotFound() throws Exception {
        mvc.perform(get("/api/states?country=does-not-exist").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void givenCities_whenGetCities_thenReturnJsonArray() throws Exception {
        String[] cities = new String[]{"City1", "City2"};
        cache.setCities("USA", "Alaska", cities);

        mvc.perform(get("/api/cities?country=USA&state=Alaska").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("City1")));
    }

    @Test
    void whenGetCitiesFromNonexistentCountryOrState_thenReturnNotFound() throws Exception {
        mvc.perform(get("/api/cities?country=does-not-exist&state=not-exists").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
}
