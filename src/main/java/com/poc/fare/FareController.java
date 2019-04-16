package com.poc.fare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fares/")
public class FareController {

    private final FareService fareService;

    @Autowired
    public FareController(FareService fareService) {
        this.fareService = fareService;
    }

    @GetMapping("{origin}/{destination}")
    public Fare getFare(@PathVariable("origin") String origin, @PathVariable("destination") String destination) {
        return fareService.get(origin, destination);
    }
}
