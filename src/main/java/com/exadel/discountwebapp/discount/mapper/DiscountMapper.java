package com.exadel.discountwebapp.discount.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.mapper.CategoryMapper;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.tag.entity.Tag;
import com.exadel.discountwebapp.tag.mapper.TagMapper;
import com.exadel.discountwebapp.tag.repository.TagRepository;
import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.mapper.VendorMapper;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiscountMapper {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    private final VendorMapper vendorMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DiscountMapper(VendorRepository vendorRepository,
                          CategoryRepository categoryRepository,
                          TagRepository tagRepository,
                          VendorMapper vendorMapper,
                          CategoryMapper categoryMapper,
                          TagMapper tagMapper) {

        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.vendorMapper = vendorMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;

        configureModelMapper();
    }

    public DiscountResponseVO toVO(Discount discount) {
        DiscountResponseVO response = modelMapper.map(discount, DiscountResponseVO.class);
        CategoryResponseVO category = categoryMapper.toVO(discount.getCategory());
        VendorResponseVO vendor = vendorMapper.toVO(discount.getVendor());
        List<TagResponseVO> tags = new ArrayList<>();
        discount.getTags().forEach(tag -> tags.add(tagMapper.toVO(tag)));

        response.setCategory(category);
        response.setVendor(vendor);
        response.setTags(tags);

        return response;
    }

    public Discount toEntity(DiscountRequestVO request) {
        Discount discount = modelMapper.map(request, Discount.class);
        provideDiscountDependencies(request, discount);
        return discount;
    }

    public void updateEntity(DiscountRequestVO request, Discount discount) {
        provideDiscountDependencies(request, discount);
        modelMapper.map(request, discount);
    }

    private void provideDiscountDependencies(DiscountRequestVO request, Discount discount) {
        Vendor vendor = vendorRepository.findById(request.getVendorId()).orElse(null);
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        List<Tag> tags = Lists.newArrayList(tagRepository.findAllById(request.getTags()));

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
            }
        };
    }
}
