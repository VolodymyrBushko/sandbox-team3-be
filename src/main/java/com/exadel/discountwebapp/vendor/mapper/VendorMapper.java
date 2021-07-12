package com.exadel.discountwebapp.vendor.mapper;

import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.mapper.LocationMapper;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendorMapper {
    private final LocationMapper locationMapper;
    private final ModelMapper modelMapper = new ModelMapper();
    private final LocationRepository locationRepository;

    @Autowired
    public VendorMapper(LocationMapper locationMapper, LocationRepository locationRepository) {
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
        configureModelMapper();
    }

    public VendorResponseVO toVO(Vendor vendor) {
        VendorResponseVO response = modelMapper.map(vendor, VendorResponseVO.class);

        List<LocationResponseVO> locations = vendor.getLocations()
                .stream().map(locationMapper::toVO)
                .collect(Collectors.toList());

        response.setLocations(locations);
        return response;
    }

    public Vendor toEntity(VendorRequestVO request) {
        var vendor = modelMapper.map(request, Vendor.class);
        provideLocationDependencies(request, vendor);
        return vendor;
    }

    public void update(VendorRequestVO request, Vendor vendor) {
        List<Location> locations = getLocationFromIds(request.getLocationIds());
        vendor.setLocations(locations);
        modelMapper.map(request, vendor);
    }

    private void provideLocationDependencies(VendorRequestVO request, Vendor vendor) {
        List<Location> locations = getLocationFromIds(request.getLocationIds());
        vendor.setLocations(locations);
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(createSkipPropertyMap());
    }

    private PropertyMap<VendorRequestVO, Vendor> createSkipPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setLocations(null);
            }
        };
    }

    private List<Location> getLocationFromIds(List<Long> listIds) {
        return listIds.stream()
                .map(e->locationRepository.findById(e).orElseThrow(()-> new EntityNotFoundException(Location.class, "id", e)))
                .collect(Collectors.toList());
    }
}
