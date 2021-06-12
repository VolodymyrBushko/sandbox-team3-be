package com.exadel.discountwebapp.location.mapper;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.vo.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public LocationMapper() {
        configureModelMapper();
    }

    public LocationResponseVO toVO(Location location) {
        return modelMapper.map(location, LocationResponseVO.class);
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
