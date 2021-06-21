package com.exadel.discountwebapp.validation;

import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VendorValidation {
    private final VendorRepository vendorRepository;

    public boolean checkDuplicateEmail(VendorRequestVO request) {
        Optional<Vendor> byEmail = vendorRepository.findByEmail(request.getEmail());
        return byEmail.isPresent();
    }
}



