package com.exadel.discountwebapp.vendor.validator;

import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorValidator {

    private final VendorRepository vendorRepository;

    public void validate(VendorRequestVO request) {
        checkDuplicateEmail(request);
    }

    public void checkDuplicateEmail(VendorRequestVO request) {
        vendorRepository.findByEmail(request.getEmail())
                .ifPresent(vendor -> {
                    throw new EntityAlreadyExistsException(Vendor.class, "email", vendor.getEmail());
                });
    }
}
