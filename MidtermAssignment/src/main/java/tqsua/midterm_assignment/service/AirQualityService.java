package tqsua.midterm_assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqsua.midterm_assignment.cache.Cache;
import tqsua.midterm_assignment.external_api.ExternalAPI;
import tqsua.midterm_assignment.model.AirQuality;

import java.util.HashMap;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AirQualityService {

    private Cache cache;
    private ExternalAPI api;

    private Logger logger;

    @Autowired
    public AirQualityService(Cache cache, ExternalAPI api) {
        this.cache = cache;
        this.api = api;
        logger = Logger.getLogger(AirQualityService.class.getName());
    }

    public AirQuality getAirQualityByCity(String country, String state, String city) {
        var aq = cache.getAirQuality(country, state, city);
        if (aq != null) {
            logger.log(Level.INFO, "Getting air quality from cache");
            return aq;
        }

        logger.log(Level.INFO, "Getting air quality from external API");

        aq = api.getAirQuality(country, state, city);
        if (aq!=null) cache.setAirQuality(country, state, city, aq);

        return aq;
    }

    public Map<String, Integer> getStatistics() {
        HashMap<String, Integer> stats = new HashMap<>();

        stats.put("count", cache.getCount());
        stats.put("hits", cache.getHits());
        stats.put("misses", cache.getMisses());

        return stats;
    }
}
