package tqsua.midterm_assignment.external_api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import tqsua.midterm_assignment.model.AirQuality;

import java.util.HashMap;
import java.util.logging.Logger;

public class ExternalAPI {
    private String baseUrl = "https://api.airvisual.com/v2/";
    private String token = "6b74a48a-8d34-43f6-92a2-bf903603543c";

    private RestTemplate restTemplate = new RestTemplate();
    private Logger logger;
    private HashMap<String,String> polluentMap = new HashMap<>();

    public ExternalAPI() {
        this.logger = Logger.getLogger(ExternalAPI.class.getName());
        this.polluentMap.put("p2", "pm2.5");
        this.polluentMap.put("p1", "pm10");
        this.polluentMap.put("o3", "Ozone O3");
        this.polluentMap.put("n2", "Nitrogen dioxide NO2");
        this.polluentMap.put("s2", "Sulfur dioxide SO2");
        this.polluentMap.put("co", "Carbon monoxide CO");
    }

    public String[] getCountries() {
        try {
            JSONArray json = (JSONArray) new JSONObject(this.restTemplate.getForObject(baseUrl+"countries?key=" + token, String.class)).get("data");

            String[] countries = new String[json.length()];
            for (int i = 0; i<json.length(); i++) {
                countries[i] = (String) ((JSONObject)json.get(i)).get("country");
            }
            return countries;
        } catch (Exception e) {
            return null;
        }

    }

    public String[] getStates(String country) {
        try {
            JSONArray json = (JSONArray) new JSONObject(this.restTemplate.getForObject(baseUrl+"states?country=" + country + "&key=" + token, String.class)).get("data");

            String[] states = new String[json.length()];
            for (int i = 0; i<json.length(); i++) {
                states[i] = (String) ((JSONObject)json.get(i)).get("state");
            }

            return states;
        } catch (Exception e) {
            return null;
        }

    }

    public String[] getCities(String country, String state) {
        try {
            JSONArray json = (JSONArray) new JSONObject(this.restTemplate.getForObject(baseUrl+"cities?state=" + state + "&country=" + country + "&key=" + token, String.class)).get("data");

            String[] cities = new String[json.length()];
            for (int i = 0; i<json.length(); i++) {
                cities[i] = (String) ((JSONObject)json.get(i)).get("city");
            }

            return cities;
        } catch (Exception e) {
            return null;
        }

    }

    public AirQuality getAirQuality(String country, String state, String city) {
        try {
            JSONObject json = (JSONObject) ((JSONObject) new JSONObject(this.restTemplate.getForObject(baseUrl+"city?city=" + city +
                    "&state=" + state + "&country=" + country + "&key=" + token, String.class)).get("data")).get("current");

            AirQuality aq = new AirQuality((Integer) ((JSONObject)json.get("weather")).get("hu"),
                    (Integer) ((JSONObject)json.get("pollution")).get("aqius"),
                    (Integer) ((JSONObject)json.get("pollution")).get("aqicn"),
                    polluentMap.get((String) ((JSONObject)json.get("pollution")).get("mainus")),
                    polluentMap.get((String) ((JSONObject)json.get("pollution")).get("maincn")));
            return aq;
        } catch (Exception e) {
            return null;
        }

    }
}