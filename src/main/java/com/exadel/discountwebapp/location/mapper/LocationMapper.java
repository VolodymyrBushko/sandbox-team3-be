package com.exadel.discountwebapp.location.mapper;

import com.exadel.discountwebapp.baseclasses.BaseEntityMapper;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.location.entity.Country;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.repository.CountryRepository;
import com.exadel.discountwebapp.location.vo.location.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper implements BaseEntityMapper<Location, LocationResponseVO> {

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;


    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public LocationMapper(CountryRepository countryRepository,
                          CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;

        configureModelMapper();
    }

    @Override
    public LocationResponseVO toVO(Location location) {
        LocationResponseVO response = modelMapper.map(location, LocationResponseVO.class);
        response.setCity(location.getCity());
        response.setAddressLine(location.getAddressLine());

        var country = countryMapper.toVO(location.getCountry());

        response.setCountryCode(country.getCountryCode());
        return response;
    }

    public Location toEntity(LocationRequestVO request) {
        var location = modelMapper.map(request, Location.class);
        provideLocationDependencies(request, location);
        return location;
    }

    public void update(Location location, LocationRequestVO request) {
        provideLocationDependencies(request, location);
        modelMapper.map(request, location);
    }

    private void provideLocationDependencies(LocationRequestVO request, Location location) {
        var country = countryRepository.findByCountryCode(request.getCountryCode())
                .orElseThrow(() -> new EntityNotFoundException(Country.class, "CountryCode", request.getCountryCode()));
        location.setCountry(country);
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(createSkipPropertyMap());
    }

    private PropertyMap<LocationRequestVO, Location> createSkipPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setCountry(null);
                skip().setVendors(null);
            }
        };
    }
}
