package com.exadel.discountwebapp.validation;

import com.exadel.discountwebapp.exception.EntityAlreadyExistsException;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VendorValidator {
    private final VendorRepository vendorRepository;

    public void validate(VendorRequestVO request) {
        checkDuplicateEmail(request);
    }

    public boolean checkDuplicateEmail(VendorRequestVO request) {
        Optional<Vendor> email = vendorRepository.findByEmail(request.getEmail());

        if (email.isPresent()) {
            throw new EntityAlreadyExistsException("Email already exist");
        } else {
            return false;
        }
    }
}



