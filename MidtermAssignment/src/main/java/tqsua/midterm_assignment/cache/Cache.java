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

    private HashMap<String, HashMap<String, HashMap<String, AirQuality>>> map = new HashMap<>();
    private Logger logger;

    public Cache() {
        logger = Logger.getLogger(Cache.class.getName());
    }

    public AirQuality getAirQuality(String country, String state, String city) {
        this.count +=1;
        if (map.containsKey(country)&&map.get(country).containsKey(state)
            &&map.get(country).get(state).containsKey(city)&&map.get(country).get(state).get(city) != null) {
            if (System.currentTimeMillis() < map.get(country).get(state).get(city).getTimeToLeave()) {
                this.hits += 1;
                return map.get(country).get(state).get(city);
            } else {
                logger.log(Level.INFO, "Air Quality item is outdated");
                map.get(country).get(state).put(city, null);
            }
        }
        this.misses += 1;
        return null;
    }

    public void setAirQuality(String country, String state, String city, AirQuality airQuality) {
        map.putIfAbsent(country, new HashMap<>());
        map.get(country).putIfAbsent(state, new HashMap<>());
        map.get(country).get(state).put(city, airQuality);
    }

    public String[] getCountries() {
        if (map.size()>0) {
            return map.keySet().stream().toArray(x -> new String[x]);
        } else {
            return new String[0];
        }
    }

    public void setCountries(String[] countries) {
        for (String country: countries) {
            map.putIfAbsent(country, new HashMap<>());
        }
    }

    public String[] getStates(String country) {
        if (map.containsKey(country) && map.get(country).size()>0) {
            return map.get(country).keySet().stream().toArray(x -> new String[x]);
        }
        return new String[0];
    }

    public String[] getCities(String country, String state) {
        if (map.containsKey(country) && map.get(country).containsKey(state) && map.get(country).get(state).size()>0) {
            return map.get(country).get(state).keySet().stream().toArray(x -> new String[x]);
        }
        return new String[0];
    }

    public void setStates(String country, String[] states) {
        map.putIfAbsent(country, new HashMap<>());
        for (String state: states) {
            map.get(country).put(state, new HashMap<>());
        }
    }

    public void setCities(String country, String state, String[] cities) {
        map.putIfAbsent(country, new HashMap<>());
        map.get(country).putIfAbsent(state, new HashMap<>());
        for (String city: cities) {
            map.get(country).get(state).put(city, null);
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
        this.map = new HashMap<>();
    }
}
