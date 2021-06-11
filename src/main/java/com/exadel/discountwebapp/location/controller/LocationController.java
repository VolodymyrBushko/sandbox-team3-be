package com.exadel.discountwebapp.location.controller;

import com.exadel.discountwebapp.location.service.LocationService;
import com.exadel.discountwebapp.location.vo.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public List<LocationResponseVO> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public LocationResponseVO getLocationById(@NotNull @Positive @PathVariable Long id) {
        return locationService.findById(id);
    }

    @GetMapping("/country")
    public List<LocationResponseVO> getAllLocationsByCountry(
            @NotBlank @Valid @RequestParam(name = "country") String country) {
        return locationService.findAllByCountry(country);
    }

    @GetMapping("/city")
    public List<LocationResponseVO> getAllLocationsByCity(
            @NotBlank @Valid @RequestParam(name = "city") String city) {
        return locationService.findAllByCity(city);
    }

    @PostMapping
    public LocationResponseVO create(
            @Valid @RequestBody LocationRequestVO request) {
        return locationService.create(request);
    }

    @PostMapping("/{id}")
    public LocationResponseVO update(
            @NotNull @Positive @PathVariable Long id,
            @Valid @RequestBody LocationRequestVO request) {
        return locationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@NotNull @Positive @PathVariable Long id) {
        locationService.deleteById(id);
    }
}
