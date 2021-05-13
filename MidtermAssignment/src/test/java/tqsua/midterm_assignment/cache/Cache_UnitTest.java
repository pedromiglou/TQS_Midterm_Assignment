package tqsua.midterm_assignment.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqsua.midterm_assignment.model.AirQuality;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Cache_UnitTest {
    private Cache cache;

    @BeforeEach
    public void setUp() {
        cache = new Cache();
    }

    @Test
    public void whenGetAirQuality_thenReturnAirQuality() {
        AirQuality aq = new AirQuality(30,40, 41, "p1", "p2");
        cache.setAirQuality("France", "Brittany", "Quimper", aq);

        AirQuality found = cache.getAirQuality("France", "Brittany", "Quimper");
        assertThat(found).isEqualTo(aq);
    }

    @Test
    public void whenGetInvalidAirQuality_thenReturnEmpty() {
        AirQuality found = cache.getAirQuality("I","N", "V");
        assertThat(found).isNull();
    }

    @Test
    public void whenGetAirQualityAfterTimeToLive_thenReturnNull() throws InterruptedException {
        AirQuality aq = new AirQuality(30,40, 41, "p1", "p1");
        aq.setTimeToLeave(System.currentTimeMillis()+1000);
        cache.setAirQuality("France", "Brittany", "Quimper", aq);

        Thread.sleep(1000L);

        AirQuality found = cache.getAirQuality("France", "Brittany", "Quimper");
        assertThat(found).isNull();
    }

    @Test
    public void whenGivenListOfCountries_thenReturnThatList() {
        cache.setCountries(new String[]{"Portugal", "Spain"});
        assertThat(cache.getCountries()).hasSize(2).contains("Portugal","Spain");
    }

    @Test
    public void whenGivenListOfStates_thenReturnThatList() {
        cache.setStates("USA", new String[]{"Alaska","Kansas"});
        assertThat(cache.getStates("USA")).hasSize(2).contains("Kansas", "Alaska");
    }

    @Test
    public void whenGivenListOfCities_thenReturnThatList() {
        cache.setCities("France", "Brittany", new String[]{"Quimper","City1", "City2"});
        assertThat(cache.getCities("France","Brittany")).hasSize(3).contains("Quimper", "City1", "City2");
    }

    @Test
    public void whenGettingCountriesBeforeSetting_thenReturnEmpty() {
        assertThat(cache.getCountries()).hasSize(0);
    }

    @Test
    public void whenGettingStatesWithUnknownCountry_thenReturnEmpty() {
        //without inserts
        assertThat(cache.getStates("A")).hasSize(0);

        //with inserts
        cache.setStates("USA", new String[]{"Kansas","Alaska"});
        assertThat(cache.getStates("A")).hasSize(0);
    }

    @Test
    public void whenGettingCitiesWithUnknownState_thenReturnEmpty() {
        //without inserts
        assertThat(cache.getCities("A","B")).hasSize(0);

        //with inserts
        cache.setCities("USA", "Alaska", new String[]{"City1","City2"});
        assertThat(cache.getCities("A","B")).hasSize(0);
    }

    @Test
    public void whenGettingMisses_thenReturnMisses() {
        assertThat(cache.getMisses()).isEqualTo(0);

        cache.getAirQuality("A","B","C");
        assertThat(cache.getMisses()).isEqualTo(1);

        cache.setAirQuality("country1","state1", "city1", new AirQuality(30,40,41,"p1","p1"));
        cache.getAirQuality("country1", "state1", "city1");
        assertThat(cache.getMisses()).isEqualTo(1);
    }

    @Test
    public void whenGettingHits_thenReturnMisses() {
        assertThat(cache.getHits()).isEqualTo(0);

        cache.getAirQuality("A","B","C");
        assertThat(cache.getHits()).isEqualTo(0);

        cache.setAirQuality("country1","state1", "city1", new AirQuality(30,40,41,"p1","p1"));
        cache.getAirQuality("country1", "state1", "city1");
        assertThat(cache.getHits()).isEqualTo(1);
    }

    @Test
    public void whenGettingCount_thenReturnCount() {
        assertThat(cache.getCount()).isEqualTo(0);

        cache.getAirQuality("A","B","C");
        assertThat(cache.getCount()).isEqualTo(1);

        cache.setAirQuality("country1","state1", "city1", new AirQuality(30,40, 41, "p1", "p1"));
        cache.getAirQuality("country1", "state1", "city1");
        assertThat(cache.getCount()).isEqualTo(2);
    }
}
