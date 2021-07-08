package com.exadel.discountwebapp.vendor.mapper;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.mapper.LocationMapper;
import com.exadel.discountwebapp.location.service.LocationService;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public VendorMapper(LocationService locationService, LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        configureModelMapper();
    }

    public VendorResponseVO toVO(Vendor vendor) {
        var response = modelMapper.map(vendor, VendorResponseVO.class);
        response.setLocation(locationMapper.toVO(vendor.getLocation()));
        return response;
    }

    public Vendor toEntity(VendorRequestVO request) {
        var vendor = modelMapper.map(request, Vendor.class);
        provideLocationDependencies(request, vendor);
        return vendor;
    }

    public void update(Vendor vendor, VendorRequestVO request) {
        provideLocationDependencies(request, vendor);
        modelMapper.map(request, vendor);
    }

    private void provideLocationDependencies(VendorRequestVO request, Vendor vendor) {
        var location = locationService.findEntityById(request.getLocationId());
        vendor.setLocation(location);
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(createSkipPropertyMap());
    }

    private PropertyMap<VendorRequestVO, Vendor> createSkipPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setLocation(null);
            }
        };
    }
}
