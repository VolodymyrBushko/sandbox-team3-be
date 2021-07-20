package com.exadel.discountwebapp.statistics.mapper;

import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedCategoryDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedDiscountViewsDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedUserDTO;
import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedVendorDTO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedCategoryVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedDiscountVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedUserVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedVendorVO;
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

    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
}
