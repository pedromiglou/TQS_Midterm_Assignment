package tqsua.midterm_assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tqsua.midterm_assignment.model.AirQuality;
import tqsua.midterm_assignment.service.AirQualityService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AirQualityController {
    @Autowired
    private AirQualityService service;

    @GetMapping("/airquality")
    public AirQuality getAirQualityByCity(@RequestParam(name="country") String country,
                    @RequestParam(name="state") String state, @RequestParam(name="city") String city) {
        var aq = service.getAirQualityByCity(country, state, city);
        if (aq== null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return aq;
    }

    @GetMapping("/statistics")
    public Map<String, Integer> getStatistics() {
        return service.getStatistics();
    }
}
