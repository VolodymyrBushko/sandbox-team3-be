package com.exadel.discountwebapp.discount.mapper;

import com.exadel.discountwebapp.category.mapper.CategoryMapper;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.mapper.LocationMapper;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.LocationResponseVO;
import com.exadel.discountwebapp.vendor.mapper.VendorMapper;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountMapper {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    private final VendorMapper vendorMapper;
    private final CategoryMapper categoryMapper;
    private final LocationMapper locationMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DiscountMapper(VendorRepository vendorRepository,
                          CategoryRepository categoryRepository,
                          LocationRepository locationRepository,
                          VendorMapper vendorMapper,
                          CategoryMapper categoryMapper,
                          LocationMapper locationMapper) {

        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.vendorMapper = vendorMapper;
        this.categoryMapper = categoryMapper;
        this.locationMapper = locationMapper;

        configureModelMapper();
    }

    public DiscountResponseVO toVO(Discount discount) {
        var response = modelMapper.map(discount, DiscountResponseVO.class);
        var category = categoryMapper.toVO(discount.getCategory());
        var vendor = vendorMapper.toVO(discount.getVendor());

        List<LocationResponseVO> locations = discount.getLocations()
                .stream().map(locationMapper::toVO)
                .collect(Collectors.toList());

        response.setLocations(locations);
        response.setCategory(category);
        response.setVendor(vendor);

        return response;
    }

    public Discount toEntity(DiscountRequestVO request) {
        var discount = modelMapper.map(request, Discount.class);
        provideDiscountDependencies(request, discount);
        return discount;
    }

    public void updateEntity(DiscountRequestVO request, Discount discount) {
        provideDiscountDependencies(request, discount);
        modelMapper.map(request, discount);
    }

    private void provideDiscountDependencies(DiscountRequestVO request, Discount discount) {
        var vendor = vendorRepository.findById(request.getVendorId()).orElse(null);
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        List<Location> locations = request.getLocationIds().stream()
                .map(locationId -> locationRepository.findById(locationId).get())
                .collect(Collectors.toList());

        discount.setLocations(locations);
        discount.setVendor(vendor);
        discount.setCategory(category);
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(createSkipPropertyMap());
    }

    private PropertyMap<DiscountRequestVO, Discount> createSkipPropertyMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                skip().setVendor(null);
                skip().setCategory(null);
            }
        };
    }
}
