package com.exadel.discountwebapp.location.mapper;

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
public class LocationMapper {

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

    public LocationResponseVO toVO(Location location) {
        LocationResponseVO response = modelMapper.map(location, LocationResponseVO.class);
        response.setCountryCode(location.getCountryCode().getCountryCode());
        return response;
    }

    public Location toEntity(LocationRequestVO request) {
        return modelMapper.map(request, Location.class);
    }

    public void update(Location location, LocationRequestVO request) {
        modelMapper.map(request, location);
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(createSkipPropertyMap());
    }

    private PropertyMap<LocationRequestVO, Location> createSkipPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setVendors(null);
            }
        };

    }
}
