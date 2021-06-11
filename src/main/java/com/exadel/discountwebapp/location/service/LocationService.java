package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.exception.LocationNotFoundException;
import com.exadel.discountwebapp.location.mapper.LocationMapper;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LocationResponseVO> findAll() {
        List<LocationResponseVO> response = new ArrayList<>();
        locationRepository.findAll().forEach(en -> response.add(locationMapper.toVO(en)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public LocationResponseVO findById(Long id) {
        Optional<Location> location = Optional.ofNullable(locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Could not find location with id: " + id)));
        return location.map(locationMapper::toVO).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Location findEntityById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LocationResponseVO> findAllByCountry(String country) {
        List<LocationResponseVO> response = new ArrayList<>();
        List<Location> locations = locationRepository.findAllByCountry(country);
        locations.forEach(entity -> response.add(locationMapper.toVO(entity)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LocationResponseVO> findAllByCity(String city) {
        List<LocationResponseVO> response = new ArrayList<>();
        List<Location> allByCountry = locationRepository.findAllByCity(city);
        allByCountry.forEach(entity -> response.add(locationMapper.toVO(entity)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public LocationResponseVO create(LocationRequestVO request) {
        return locationMapper.toVO(locationRepository.save(locationMapper.toEntity(request)));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public LocationResponseVO update(Long id, LocationRequestVO request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Could not find location with id: " + id));
        locationMapper.updateVO(location, request);
        locationRepository.save(location);
        return locationMapper.toVO(location);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}
