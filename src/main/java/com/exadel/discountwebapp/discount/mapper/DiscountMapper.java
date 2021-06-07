package com.exadel.discountwebapp.discount.mapper;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.category.repository.CategoryRepository;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountMapper {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public DiscountResponseVO toVO(Discount discount) {
        return DiscountResponseVO.builder()
                .id(discount.getId())
                .title(discount.getTitle())
                .shortDescription(discount.getShortDescription())
                .description(discount.getDescription())
                .imageUrl(discount.getImageUrl())
                .flatAmount(discount.getFlatAmount())
                .price(discount.getPrice())
                .startDate(discount.getStartDate())
                .expirationDate(discount.getExpirationDate())
                .percentage(discount.getPercentage())
                .quantity(discount.getQuantity())
                .perUser(discount.getPerUser())
                .categoryId(discount.getCategory().getId())
                .vendorId(discount.getVendor().getId())
                .build();
    }

    public Discount toEntity(DiscountRequestVO request) {

        Vendor vendor = vendorRepository.findById(request.getVendorId()).orElse(null);
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        return Discount.builder()
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .flatAmount(request.getFlatAmount())
                .price(request.getPrice())
                .startDate(request.getStartDate())
                .expirationDate(request.getExpirationDate())
                .percentage(request.getPercentage())
                .quantity(request.getQuantity())
                .perUser(request.getPerUser())
                .category(category)
                .vendor(vendor)
                .build();
    }
}
