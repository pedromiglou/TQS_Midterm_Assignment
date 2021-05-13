package tqsua.midterm_assignment.cache;

import tqsua.midterm_assignment.model.AirQuality;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cache {
    //although i will have countries, states and cities in the cache,
    //these statistics will only account for air quality requests
    private int misses = 0;
    private int hits = 0;
    private int count = 0;

    private HashMap<String, HashMap<String, HashMap<String, AirQuality>>> cache = new HashMap<>();
    private Logger logger;

    public Cache() {
        logger = Logger.getLogger(Cache.class.getName());
    }

    public AirQuality getAirQuality(String country, String state, String city) {
        this.count +=1;
        if (cache.containsKey(country)) {
            if (cache.get(country).containsKey(state)) {
                if (cache.get(country).get(state).containsKey(city)) {
                    if (cache.get(country).get(state).get(city) != null) {
                        if (System.currentTimeMillis() < cache.get(country).get(state).get(city).getTimeToLeave()) {
                            this.hits += 1;
                            return cache.get(country).get(state).get(city);
                        } else {
                            logger.log(Level.INFO, "Air Quality item is outdated");
                            cache.get(country).get(state).put(city, null);
                        }
                    }
                }
            }
        }
        this.misses += 1;
        return null;
    }

    public void setAirQuality(String country, String state, String city, AirQuality airQuality) {
        if (!cache.containsKey(country)) {
            cache.put(country, new HashMap<>());
        }
        if (!cache.get(country).containsKey(state)) {
            cache.get(country).put(state, new HashMap<>());
        }
        cache.get(country).get(state).put(city, airQuality);
    }

    public String[] getCountries() {
        if (cache.size()>0) {
            return cache.keySet().stream().toArray(x -> new String[x]);
        } else {
            return null;
        }
    }

    public void setCountries(String[] countries) {
        for (String country: countries) {
            if (!cache.containsKey(country)) {
                this.cache.put(country, new HashMap<>());
            }
        }
    }

    public String[] getStates(String country) {
        if (cache.containsKey(country)) {
            if (cache.get(country).size()>0) {
                return cache.get(country).keySet().stream().toArray(x -> new String[x]);
            }
        }
        return null;
    }

    public String[] getCities(String country, String state) {
        if (cache.containsKey(country)) {
            if (cache.get(country).containsKey(state)) {
                if (cache.get(country).get(state).size()>0) {
                    return cache.get(country).get(state).keySet().stream().toArray(x -> new String[x]);
                }
            }
        }
        return null;
    }

    public void setStates(String country, String[] states) {
        if (!cache.containsKey(country)) {
            cache.put(country, new HashMap<>());
        }
        for (String state: states) {
            cache.get(country).put(state, new HashMap<>());
        }
    }

    public void setCities(String country, String state, String[] cities) {
        if (!cache.containsKey(country)) {
            cache.put(country, new HashMap<>());
        }
        if (!cache.get(country).containsKey(state)) {
            cache.get(country).put(state, new HashMap<>());
        }
        for (String city: cities) {
            cache.get(country).get(state).put(city, null);
        }
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void clean() {
        this.cache = new HashMap<>();
    }
}
