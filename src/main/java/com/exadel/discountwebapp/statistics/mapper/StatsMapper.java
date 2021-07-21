package com.exadel.discountwebapp.statistics.mapper;

import com.exadel.discountwebapp.statistics.dto.extendeddto.*;
import com.exadel.discountwebapp.statistics.extendedvo.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class StatsMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public StatsMapper() {
        configureModelMapper();
    }

    public ExtendedVendorVO vendorToVO(ExtendedVendorDTO data) {
        return modelMapper.map(data, ExtendedVendorVO.class);
    }

    public ExtendedCategoryVO categoryToVO(ExtendedCategoryDTO data) {
        return modelMapper.map(data, ExtendedCategoryVO.class);
    }

    public ExtendedUserVO userToVO(ExtendedUserDTO data) {
        return modelMapper.map(data, ExtendedUserVO.class);
    }

    public ExtendedDiscountVO discountToVO(ExtendedDiscountViewsDTO data) {
        return modelMapper.map(data, ExtendedDiscountVO.class);
    }

    public ExtendedUsersPreferenceVO urPrefToVO(ExtendedUsersPreferenceDTO data) {
        return modelMapper.map(data, ExtendedUsersPreferenceVO.class);
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
}
