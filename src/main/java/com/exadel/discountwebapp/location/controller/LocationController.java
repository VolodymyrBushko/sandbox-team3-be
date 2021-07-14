package com.exadel.discountwebapp.location.controller;

import com.exadel.discountwebapp.location.service.CountryService;
import com.exadel.discountwebapp.location.service.LocationService;
import com.exadel.discountwebapp.location.vo.country.CountryResponseVO;
import com.exadel.discountwebapp.location.vo.location.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final CountryService countryService;

    @GetMapping
    public Page<LocationResponseVO> findAllLocations(@RequestParam(value = "query", defaultValue = "", required = false) String query,
                                                     @PageableDefault(sort = {"country"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return locationService.findAll(query, pageable);
    }

    @GetMapping("/{id}")
    public LocationResponseVO getLocationById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    // TODO: tests for this controller
    @GetMapping("/countries")
    public List<CountryResponseVO> getAllCountries() {
        return countryService.findAllCountries();
    }

    // TODO: tests for this controller
    @GetMapping("/cities")
    public List<String> getAllCitiesByCountry(
            @RequestParam(name = "countryCode") String countryCode) {
        return locationService.findAllCitiesByCountryCode(countryCode);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public LocationResponseVO create(@Valid @RequestBody LocationRequestVO request) {
        return locationService.createIfNotExist(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public LocationResponseVO update(@PathVariable Long id, @Valid @RequestBody LocationRequestVO request) {
        return locationService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locationService.deleteById(id);
    }
}
