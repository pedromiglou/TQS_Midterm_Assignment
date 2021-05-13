package tqsua.midterm_assignment.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqsua.midterm_assignment.service.CityService;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
public class CityController_UnitTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService service;

    @Test
    public void givenCountries_whenGetCountries_thenReturnJsonArray() throws Exception {
        String[] countries = new String[]{"USA", "France"};

        given(service.getCountries()).willReturn(countries);

        mvc.perform(get("/api/countries").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("USA")));
        verify(service, VerificationModeFactory.times(1)).getCountries();
    }

    @Test
    public void givenStates_whenGetStates_thenReturnJsonArray() throws Exception {
        String[] states = new String[]{"Alaska", "Kansas"};

        given(service.getStates("USA")).willReturn(states);

        mvc.perform(get("/api/states?country=USA").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("Alaska")));
        verify(service, VerificationModeFactory.times(1)).getStates("USA");
    }

    @Test
    public void whenGetStatesFromNonexistentCountry_thenReturnNotFound() throws Exception {
        given(service.getStates("-does-not-exist-")).willReturn(null);

        mvc.perform(get("/api/states?country=-does-not-exist-").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
        verify(service, VerificationModeFactory.times(1)).getStates("-does-not-exist-");
    }

    @Test
    public void givenCities_whenGetCities_thenReturnJsonArray() throws Exception {
        String[] cities = new String[]{"City1", "City2"};
        given(service.getCities("USA", "Alaska")).willReturn(cities);

        mvc.perform(get("/api/cities?country=USA&state=Alaska").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("City1")));
        verify(service, VerificationModeFactory.times(1)).getCities("USA", "Alaska");
    }

    @Test
    public void whenGetCitiesFromNonexistentCountryOrState_thenReturnNotFound() throws Exception {
        given(service.getCities("-does-not-exist-", "not-exists")).willReturn(null);

        mvc.perform(get("/api/cities?country=-does-not-exist-&state=not-exists").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
        verify(service, VerificationModeFactory.times(1)).getCities("-does-not-exist-", "not-exists");
    }
}
