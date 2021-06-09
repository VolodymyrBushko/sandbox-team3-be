package com.exadel.discountwebapp.location.controller;

import com.exadel.discountwebapp.location.service.LocationService;
import com.exadel.discountwebapp.location.vo.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@AllArgsConstructor
public class LocationController {
    private LocationService locationService;

    @GetMapping
    public List<LocationResponseVO> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public LocationResponseVO getLocationById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @GetMapping("/country")
    @ResponseBody
    public List<LocationResponseVO> getAllLocationsByCounty(@RequestParam(name="country") String county) {
        return locationService.findAllByCounty(county);
    }

    @GetMapping("/city")
    @ResponseBody
    public List<LocationResponseVO> getAllLocationsByCity(@RequestParam(name="city") String city) {
        return locationService.findAllByCity(city);
    }

    @PostMapping
    public LocationResponseVO save(@RequestBody LocationRequestVO request) {
        return locationService.create(request);
    }

    @PostMapping("/{id}")
    public LocationResponseVO update(@PathVariable Long id, @RequestBody LocationRequestVO request) {
        return locationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locationService.deleteById(id);
    }
}
