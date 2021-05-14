package tqsua.midterm_assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tqsua.midterm_assignment.service.CityService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CityController {
    @Autowired
    private CityService service;

    @GetMapping("/countries")
    public String[] getCountries() {
        String[] countries = service.getCountries();
        if (countries.length==0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return countries;
    }

    @GetMapping("/states")
    public String[] getStates(@RequestParam("country") String country) {
        String[] states = service.getStates(country);
        if (states.length==0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return states;
    }

    @GetMapping("/cities")
    public String[] getCities(@RequestParam("country") String country, @RequestParam("state") String state) {
        String[] cities = service.getCities(country, state);
        if (cities.length==0) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return cities;
    }
}
