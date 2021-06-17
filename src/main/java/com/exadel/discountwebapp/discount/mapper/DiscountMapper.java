package com.exadel.discountwebapp.discount.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.mapper.CategoryMapper;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.category.vo.CategoryResponseVO;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.mapper.VendorMapper;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    private final VendorMapper vendorMapper;
    private final CategoryMapper categoryMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DiscountMapper(VendorRepository vendorRepository,
                          CategoryRepository categoryRepository,
                          VendorMapper vendorMapper,
                          CategoryMapper categoryMapper) {

        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
        this.vendorMapper = vendorMapper;
        this.categoryMapper = categoryMapper;

        configureModelMapper();
    }

    public DiscountResponseVO toVO(Discount discount) {
        DiscountResponseVO response = modelMapper.map(discount, DiscountResponseVO.class);
        CategoryResponseVO category = categoryMapper.toVO(discount.getCategory());
        VendorResponseVO vendor = vendorMapper.toVO(discount.getVendor());

        response.setCategory(category);
        response.setVendor(vendor);

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
