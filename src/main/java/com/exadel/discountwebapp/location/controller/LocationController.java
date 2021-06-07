package com.exadel.discountwebapp.location.controller;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationController {
    LocationService locationService;

    @GetMapping("/")
    private List<Location> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{county}")
    private List<Location> getALlLocationByCounty(@PathVariable String county) {
        return locationService.findAllByCounty(county);
    }

    @GetMapping("/{city}")
    private List<Location> getAllLocationByCity(@PathVariable String city) {
        return locationService.findAllByCity(city);
    }

    @PostMapping
    private Location save(@RequestBody Location location) {
        return locationService.create(location);
    }

    @PostMapping("{/id}")
    private Location update(@PathVariable Long id, @RequestBody Location location) {
        locationService.update(location);
        return locationService.findById(id);
    }

    @DeleteMapping("/{/id}")
    void delete(@PathVariable Long id) {
        locationService.deleteById(id);
    }
}
