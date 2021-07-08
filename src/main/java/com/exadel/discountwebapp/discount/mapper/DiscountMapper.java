package com.exadel.discountwebapp.discount.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.mapper.CategoryMapper;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.location.entity.Location;
import com.exadel.discountwebapp.location.mapper.LocationMapper;
import com.exadel.discountwebapp.location.repository.LocationRepository;
import com.exadel.discountwebapp.location.vo.location.LocationResponseVO;
import com.exadel.discountwebapp.tag.mapper.TagMapper;
import com.exadel.discountwebapp.tag.repository.TagRepository;
import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.mapper.VendorMapper;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.google.common.collect.Lists;
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
    private final TagRepository tagRepository;
    private final LocationRepository locationRepository;

    private final VendorMapper vendorMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final LocationMapper locationMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DiscountMapper(VendorRepository vendorRepository,
                          CategoryRepository categoryRepository,
                          TagRepository tagRepository,
                          LocationRepository locationRepository,
                          VendorMapper vendorMapper,
                          CategoryMapper categoryMapper,
                          TagMapper tagMapper,
                          LocationMapper locationMapper) {

        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.locationRepository = locationRepository;
        this.vendorMapper = vendorMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.locationMapper = locationMapper;

        configureModelMapper();
    }

    public DiscountResponseVO toVO(Discount discount) {
        var response = modelMapper.map(discount, DiscountResponseVO.class);
        var category = categoryMapper.toVO(discount.getCategory());
        var vendor = vendorMapper.toVO(discount.getVendor());

        List<TagResponseVO> tags = discount.getTags()
                .stream().map(tagMapper::toVO)
                .collect(Collectors.toList());

        List<LocationResponseVO> locations = discount.getLocations().stream().map(locationMapper::toVO)
                                             .collect(Collectors.toList());

        response.setLocations(locations);
        response.setCategory(category);
        response.setVendor(vendor);
        response.setTags(tags);

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
        var vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new EntityNotFoundException(Vendor.class, "id", request.getVendorId()));
        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(Category.class, "id", request.getCategoryId()));

        var locations = Lists.newArrayList(locationRepository.findAllById(request.getLocationIds()));

        var tags = Lists.newArrayList(tagRepository.findAllById(request.getTagIds()));

        if (request.getLocationIds().size() != locations.size())
            throw new EntityNotFoundException(Location.class, "id", "...");

        discount.setLocations(locations);
        discount.setVendor(vendor);
        discount.setCategory(category);
        discount.setTags(tags);
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
                skip().setTags(null);
                skip().setLocations(null);
            }
        };
    }
}
