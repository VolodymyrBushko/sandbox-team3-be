package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.exception.EntityNotFoundException;
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

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LocationResponseVO> findAll() {
        List<Location> locations = (List<Location>) locationRepository.findAll();
        return getLocationResponseVO(locations);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public LocationResponseVO findById(Long id) {
        return locationMapper.toVO(getLocationById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Location findEntityById(Long id) {
        return getLocationById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LocationResponseVO> findAllByCountry(String country) {
        List<Location> locations = locationRepository.findAllByCountry(country);
        return getLocationResponseVO(locations);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LocationResponseVO> findAllByCity(String city) {
        List<Location> locations = locationRepository.findAllByCity(city);
        return getLocationResponseVO(locations);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LocationResponseVO create(LocationRequestVO request) {
        return locationMapper.toVO(locationRepository.save(locationMapper.toEntity(request)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LocationResponseVO update(Long id, LocationRequestVO request) {
        Location location = getLocationById(id);
        locationMapper.update(location, request);
        locationRepository.save(location);
        return locationMapper.toVO(location);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find location with id: " + id));
    }

    private List<LocationResponseVO> getLocationResponseVO(List<Location> locations) {
        List<LocationResponseVO> response = new ArrayList<>();
        locations.forEach(entity -> response.add(locationMapper.toVO(entity)));
        return response;
    }
}
