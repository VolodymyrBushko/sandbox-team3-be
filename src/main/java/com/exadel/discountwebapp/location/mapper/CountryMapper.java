package com.exadel.discountwebapp.location.mapper;

import com.exadel.discountwebapp.location.entity.Country;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.vo.country.CountryResponseVO;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    private final Country country = new Country();

    @Autowired
    public CountryMapper() {
        configureModelMapper();
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public CountryResponseVO toVO(Country country) {
        return modelMapper.map(country, CountryResponseVO.class);
    }
}
