package com.exadel.discountwebapp.vendor.validator;

import com.exadel.discountwebapp.exception.EntityAlreadyExistsException;
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
        if (vendorRepository.existsByEmail(request.getEmail()))
            throw new EntityAlreadyExistsException(
                    String.format("Vendor with email \"%s\" already exist", request.getEmail()));
    }
}