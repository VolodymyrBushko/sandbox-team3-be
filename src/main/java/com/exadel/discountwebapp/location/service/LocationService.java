package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.filter.SpecificationBuilder;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.mapper.LocationMapper;
import com.exadel.discountwebapp.location.projections.CityView;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.location.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import lombok.RequiredArgsConstructor;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<LocationResponseVO> findAll(String query, Pageable pageable) {
        SpecificationBuilder<Location> specificationBuilder = new SpecificationBuilder<>();
        Specification<Location> specification = specificationBuilder.fromQuery(query);

        Page<Location> page = locationRepository.findAll(specification, pageable);
        return page.map(locationMapper::toVO);
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
    public List<String> findAllCitiesByCountryCode(String countryCode) {
        return locationRepository.findAllByCountry_CountryCode(countryCode)
                .stream()
                .map(CityView::getCity)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LocationResponseVO createIfNotExist(LocationRequestVO request) {
        var existedLocation = locationRepository.findByCountry_CountryCodeAndCityAndAddressLine(
                request.getCountryCode(),
                request.getCity(),
                request.getAddressLine());

        return existedLocation == null ?
                locationMapper.toVO(locationRepository.save(locationMapper.toEntity(request)))
                : locationMapper.toVO(existedLocation);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public LocationResponseVO update(Long id, LocationRequestVO request) {
        var location = getLocationById(id);
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
                .orElseThrow(() -> new EntityNotFoundException(Location.class, "id", id));
    }
}
