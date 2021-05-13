package tqsua.midterm_assignment.external_api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ExternalAPI_UnitTest {
    private ExternalAPI api = new ExternalAPI();

    @Test
    void whenGetCountries_thenReturnCountries() {
        assertThat(api.getCountries()).isInstanceOf(String[].class);
    }

    @Test
    void whenGetStatesFromValidCountry_thenReturnStates() {
        assertThat(api.getStates("USA")).isInstanceOf(String[].class);
    }

    @Test
    void whenGetStatesFromInvalidCountry_thenReturnEmpty() {
        assertThat(api.getStates("does_not_exist")).isEmpty();
    }

    @Test
    void whenGetCitiesFromValidState_thenReturnCities() {
        assertThat(api.getCities("USA", "Alaska")).isInstanceOf(String[].class);
    }

    @Test
    void whenGetCitiesFromInvalidState_thenReturnEmpty() {
        assertThat(api.getCities("does_not_exist","does_not_exist_either")).isEmpty();
    }
}
