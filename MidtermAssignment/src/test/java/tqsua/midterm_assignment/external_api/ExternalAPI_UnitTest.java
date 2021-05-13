package tqsua.midterm_assignment.external_api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ExternalAPI_UnitTest {
    private ExternalAPI api = new ExternalAPI();

    @Test
    public void whenGetCountries_thenReturnCountries() {
        assertThat(api.getCountries()).isInstanceOf(String[].class);
    }

    @Test
    public void whenGetStatesFromValidCountry_thenReturnStates() {
        assertThat(api.getStates("USA")).isInstanceOf(String[].class);
    }

    @Test
    public void whenGetStatesFromInvalidCountry_thenReturnEmpty() {
        assertThat(api.getStates("does_not_exist")).hasSize(0);
    }

    @Test
    public void whenGetCitiesFromValidState_thenReturnCities() {
        assertThat(api.getCities("USA", "Alaska")).isInstanceOf(String[].class);
    }

    @Test
    public void whenGetCitiesFromInvalidState_thenReturnEmpty() {
        assertThat(api.getCities("does_not_exist","does_not_exist_either")).hasSize(0);
    }
}
