package com.exadel.discountwebapp.vendor.mapper;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.service.LocationService;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VendorMapper {
    private LocationRepository locationRepository;

    public Vendor toEntity(VendorRequestVO request) {

        Location location = locationRepository.findById(request.getLocationId()).orElse(null);

        return Vendor.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .email(request.getEmail())
                .location(location)
                .build();
    }

    public VendorResponseVO toResponseVO(Vendor vendor) {
        return VendorResponseVO.builder()
                .id(vendor.getId())
                .title(vendor.getTitle())
                .description(vendor.getDescription())
                .imageUrl(vendor.getImageUrl())
                .email(vendor.getEmail())
                .locationId(vendor.getLocation().getId())
                .build();
    }

    public Vendor updateVO(Vendor vendor, VendorRequestVO request){
        Location location = locationRepository.findById(request.getLocationId()).orElse(null);

        vendor.setTitle((request.getTitle()));
        vendor.setDescription(request.getDescription());
        vendor.setImageUrl(request.getImageUrl());
        vendor.setEmail(request.getEmail());
        vendor.setLocation(location);
        return vendor;
    }
}
