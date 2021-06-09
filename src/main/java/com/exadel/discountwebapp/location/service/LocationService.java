package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.location.entity.Location;
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

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional(readOnly = true)
    public List<LocationResponseVO> findAll() {
        List<LocationResponseVO> response = new ArrayList<>();
        locationRepository.findAll().forEach(en -> response.add(locationMapper.toResponseVO(en)));
        return response;
    }

    @Transactional(readOnly = true)
    public LocationResponseVO findById(Long id) {
        Location location = locationRepository.findById(id).orElse(null);
        return location != null ? locationMapper.toResponseVO(location) : null;
    }

    @Transactional(readOnly = true)
    public Location findEntityById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<LocationResponseVO> findAllByCounty(String country) {
        List<LocationResponseVO> response = new ArrayList<>();
        List<Location> allByCountry = locationRepository.findAllByCountry(country);
        allByCountry.forEach(en -> response.add(locationMapper.toResponseVO(en)));
        return response;
    }

    @Transactional(readOnly = true)
    public List<LocationResponseVO> findAllByCity(String city) {
        List<LocationResponseVO> response = new ArrayList<>();
        List<Location> allByCountry = locationRepository.findAllByCity(city);
        allByCountry.forEach(en -> response.add(locationMapper.toResponseVO(en)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LocationResponseVO create(LocationRequestVO request) {
        return locationMapper.toResponseVO(locationRepository.save(locationMapper.toEntity(request)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LocationResponseVO update(Long id, LocationRequestVO request) {
        Location location = locationRepository.findById(id).orElse(null);
        Location updatedLocation = locationMapper.updateVO(location, request);
        return locationMapper.toResponseVO(locationRepository.save(updatedLocation));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}
