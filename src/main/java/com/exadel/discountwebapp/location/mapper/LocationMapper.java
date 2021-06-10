package com.exadel.discountwebapp.location.mapper;

import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.vo.LocationRequestVO;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LocationMapper {

    public Location toEntity(LocationRequestVO request) {
        return Location.builder()
                .country(request.getCountry())
                .city(request.getCity())
                .build();
    }

    public LocationResponseVO toResponseVO(Location location) {
        return LocationResponseVO.builder()
                .id(location.getId())
                .country(location.getCountry())
                .city(location.getCity())
                .build();
    }

    public Location update(Location location, LocationRequestVO request) {
        location.setCountry(request.getCountry());
        location.setCity(request.getCity());
        return location;
    }
}
