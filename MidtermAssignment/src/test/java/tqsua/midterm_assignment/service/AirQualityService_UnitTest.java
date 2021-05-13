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
import tqsua.midterm_assignment.model.AirQuality;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class AirQualityService_UnitTest {
    @Mock(lenient = true)
    private Cache cache;

    @Mock(lenient = true)
    private ExternalAPI api;

    @InjectMocks
    private AirQualityService service;

    @BeforeEach
    void setUp() {
        AirQuality aq = new AirQuality(30,40,41,"p1","p2");
        Mockito.when(cache.getAirQuality("France", "Brittany", "Quimper")).thenReturn(aq);
        Mockito.when(api.getAirQuality("France", "Brittany", "Quimper")).thenReturn(aq);
        Mockito.when(cache.getAirQuality("France", "Burgundy", "Burgundy")).thenReturn(null);
        Mockito.when(api.getAirQuality("France", "Burgundy", "Burgundy")).thenReturn(aq);
        Mockito.when(cache.getAirQuality("USA", "Alaska", "City1")).thenReturn(null);
        Mockito.when(api.getAirQuality("USA", "Alaska", "City1")).thenReturn(null);
        Mockito.when(cache.getCount()).thenReturn(3);
        Mockito.when(cache.getMisses()).thenReturn(2);
        Mockito.when(cache.getHits()).thenReturn(1);
    }

    @Test
    void whenAirQualityExistsInCache_thenItShouldBeFoundOnTheCache() {
        AirQuality found = service.getAirQualityByCity("France", "Brittany", "Quimper");
        assertThat(found.getHumidity()).isEqualTo(30);
        Mockito.verify(cache, VerificationModeFactory.times(1)).getAirQuality("France", "Brittany", "Quimper");
        Mockito.verify(api, VerificationModeFactory.times(0)).getAirQuality("France", "Brittany", "Quimper");
    }

    @Test
    void whenAirQualityExistsOnlyInExternalAPI_thenItShouldBeFoundOnTheAPI() {
        AirQuality found = service.getAirQualityByCity("France", "Burgundy", "Burgundy");
        assertThat(found.getHumidity()).isEqualTo(30);
        Mockito.verify(cache, VerificationModeFactory.times(1)).getAirQuality("France", "Burgundy", "Burgundy");
        Mockito.verify(api, VerificationModeFactory.times(1)).getAirQuality("France", "Burgundy", "Burgundy");
    }

    @Test
    void whenAirQualityDoesNotExist_thenItShouldNotBeFound() {
        AirQuality found = service.getAirQualityByCity("USA","Alaska", "City1");
        assertThat(found).isNull();
        Mockito.verify(cache, VerificationModeFactory.times(1)).getAirQuality("USA", "Alaska", "City1");
        Mockito.verify(api, VerificationModeFactory.times(1)).getAirQuality("USA", "Alaska", "City1");
    }

    @Test
    void whenGettingStatistics_thenItShouldReturnStatistics() {
        assertThat(service.getStatistics()).hasFieldOrPropertyWithValue("count", 3);
        assertThat(service.getStatistics()).hasFieldOrPropertyWithValue("misses", 2);
        assertThat(service.getStatistics()).hasFieldOrPropertyWithValue("hits", 1);
        Mockito.verify(cache, VerificationModeFactory.times(3)).getCount();
        Mockito.verify(cache, VerificationModeFactory.times(3)).getMisses();
        Mockito.verify(cache, VerificationModeFactory.times(3)).getHits();
    }
}
