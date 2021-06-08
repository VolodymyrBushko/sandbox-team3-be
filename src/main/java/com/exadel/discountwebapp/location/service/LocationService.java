package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocationService {
    LocationRepository locationRepository;

    public List<Location> findAll() {
        return (List<Location>) locationRepository.findAll();
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public Location findEntityById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }



    public List<Location> findAllByCounty(String country) {
        return locationRepository.findAllByCountry(country);
    }

    public List<Location> findAllByCity(String city) {
        return locationRepository.findAllByCity(city);
    }

    public Location create(Location location) {
        return locationRepository.save(location);
    }

    public Location update(Location location) {
        return locationRepository.save(location);
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}
