package tqsua.midterm_assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqsua.midterm_assignment.cache.Cache;
import tqsua.midterm_assignment.external_api.ExternalAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityService {

    private Cache cache;
    private ExternalAPI api;
    private Logger logger;

    @Autowired
    public CityService(Cache cache, ExternalAPI api) {
        this.cache = cache;
        this.logger = Logger.getLogger(CityService.class.getName());
        this.api = api;
    }

    public String[] getCountries() {
        String[] countries = cache.getCountries();
        if (countries.length>0) {
            logger.log(Level.INFO, "Getting countries from cache");
            return countries;
        }
        logger.log(Level.INFO, "Getting countries from external API");

        countries = api.getCountries();
        if (countries.length>0) cache.setCountries(countries);

        return countries;
    }

    public String[] getStates(String country) {
        String[] states = cache.getStates(country);
        if (states.length>0) {
            logger.log(Level.INFO, "Getting states from cache");
            return states;
        }
        logger.log(Level.INFO, "Getting states from external API");

        states = api.getStates(country);
        if (states.length>0) cache.setStates(country, states);

        return states;
    }

    public String[] getCities(String country, String state) {
        String[] cities = cache.getCities(country, state);
        if (cities.length>0) {
            logger.log(Level.INFO, "Getting cities from cache");
            return cities;
        }

        logger.log(Level.INFO, "Getting cities from external API");

        cities = api.getCities(country, state);
        if (cities.length>0) cache.setCities(country, state, cities);

        return cities;
    }
}
