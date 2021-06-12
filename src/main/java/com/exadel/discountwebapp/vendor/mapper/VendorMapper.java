package com.exadel.discountwebapp.vendor.mapper;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.service.LocationService;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorMapper {
    private LocationService locationService;
    private ModelMapper modelMapper = new ModelMapper();

    public VendorMapper(LocationService locationService) {
        this.locationService = locationService;
        configureModelMapper();
    }

    public VendorResponseVO toVO(Vendor vendor) {
        return modelMapper.map(vendor, VendorResponseVO.class);
    }

    public Vendor toEntity(VendorRequestVO request) {
        Vendor vendor = modelMapper.map(request, Vendor.class);
        provideLocationDependencies(request, vendor);
        return vendor;
    }

    public void update(Vendor vendor, VendorRequestVO request) {
        provideLocationDependencies(request, vendor);
        modelMapper.map(request, vendor);
    }

    private void provideLocationDependencies(VendorRequestVO request, Vendor vendor) {
        Location location = locationService.findEntityById(request.getLocationId());
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
