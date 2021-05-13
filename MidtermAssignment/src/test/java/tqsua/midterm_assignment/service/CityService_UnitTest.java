package tqsua.midterm_assignment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqsua.midterm_assignment.cache.Cache;
import tqsua.midterm_assignment.external_api.ExternalAPI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CityService_UnitTest {
    @Mock(lenient = true)
    private Cache cache;

    @Mock(lenient = true)
    private ExternalAPI api;

    @InjectMocks
    private CityService service;

    @BeforeEach
    public void setUp() {
        Mockito.when(cache.getStates("USA")).thenReturn(new String[]{"Alaska", "Kansas"});
        Mockito.when(api.getStates("USA")).thenReturn(new String[]{"Alaska", "Kansas"});
        Mockito.when(cache.getStates("France")).thenReturn(new String[0]);
        Mockito.when(api.getStates("France")).thenReturn(new String[]{"Brittany", "Burgundy"});

        Mockito.when(cache.getCities("USA", "Alaska")).thenReturn(new String[]{"City1", "City2"});
        Mockito.when(api.getCities("USA", "Alaska")).thenReturn(new String[]{"City1", "City2"});
        Mockito.when(cache.getCities("France", "Brittany")).thenReturn(new String[0]);
        Mockito.when(api.getCities("France", "Brittany")).thenReturn(new String[]{"City1", "City2"});

        Mockito.when(cache.getStates("NotReal")).thenReturn(new String[0]);
        Mockito.when(api.getStates("NotReal")).thenReturn(new String[0]);
        Mockito.when(cache.getCities("NotReal","A")).thenReturn(new String[0]);
        Mockito.when(api.getCities("NotReal", "A")).thenReturn(new String[0]);
    }

    @Test
    public void whenCountriesExistInCache_thenTheyShouldBeFoundOnTheCache() {
        String[] countries = new String[]{"USA", "France"};
        Mockito.when(cache.getCountries()).thenReturn(countries);
        Mockito.when(api.getCountries()).thenReturn(countries);

        String[] found = service.getCountries();
        assertThat(found).hasSize(2).contains("USA", "France");
        Mockito.verify(cache, VerificationModeFactory.times(1)).getCountries();
        Mockito.verify(api, VerificationModeFactory.times(0)).getCountries();
    }

    @Test
    public void whenCountriesExistOnlyInExternalAPI_thenTheyShouldBeFoundOnTheAPI() {
        String[] countries = new String[]{"USA", "France"};
        Mockito.when(cache.getCountries()).thenReturn(new String[0]);
        Mockito.when(api.getCountries()).thenReturn(countries);

        String[] found = service.getCountries();
        assertThat(found).hasSize(2).contains("USA", "France");
        Mockito.verify(cache, VerificationModeFactory.times(1)).getCountries();
        Mockito.verify(api, VerificationModeFactory.times(1)).getCountries();
    }

    @Test
    public void whenStatesExistInCache_thenTheyShouldBeFoundOnTheCache() {
        String[] found = service.getStates("USA");
        assertThat(found).hasSize(2).contains("Alaska", "Kansas");
        Mockito.verify(cache, VerificationModeFactory.times(1)).getStates("USA");
        Mockito.verify(api, VerificationModeFactory.times(0)).getStates("USA");
    }

    @Test
    public void whenStatesExistOnlyInExternalAPI_thenTheyShouldBeFoundOnTheAPI() {
        String[] found = service.getStates("France");
        assertThat(found).hasSize(2).contains("Burgundy", "Brittany");
        Mockito.verify(cache, VerificationModeFactory.times(1)).getStates("France");
        Mockito.verify(api, VerificationModeFactory.times(1)).getStates("France");
    }

    @Test
    public void whenCitiesExistInCache_thenTheyShouldBeFoundOnTheCache() {
        String[] found = service.getCities("USA", "Alaska");
        assertThat(found).hasSize(2).contains("City1", "City2");
        Mockito.verify(cache, VerificationModeFactory.times(1)).getCities("USA", "Alaska");
        Mockito.verify(api, VerificationModeFactory.times(0)).getCities("USA", "Alaska");
    }

    @Test
    public void whenCitiesExistOnlyInExternalAPI_thenTheyShouldBeFoundOnTheAPI() {
        String[] found = service.getCities("France", "Brittany");
        assertThat(found).hasSize(2).contains("City1", "City2");
        Mockito.verify(cache, VerificationModeFactory.times(1)).getCities("France", "Brittany");
        Mockito.verify(api, VerificationModeFactory.times(1)).getCities("France", "Brittany");
    }

    @Test
    public void whenCountryDoesNotExist_thenStatesShouldNotBeFound() {
        String[] found = service.getStates("NotReal");
        assertThat(found).hasSize(0);
        Mockito.verify(cache, VerificationModeFactory.times(1)).getStates("NotReal");
        Mockito.verify(api, VerificationModeFactory.times(1)).getStates("NotReal");
    }

    @Test
    public void whenStateOrCountryDoesNotExist_thenCitiesShouldNotBeFound() {
        String[] found = service.getCities("NotReal", "A");
        assertThat(found).hasSize(0);
        Mockito.verify(cache, VerificationModeFactory.times(1)).getCities("NotReal", "A");
        Mockito.verify(api, VerificationModeFactory.times(1)).getCities("NotReal", "A");
    }
}
