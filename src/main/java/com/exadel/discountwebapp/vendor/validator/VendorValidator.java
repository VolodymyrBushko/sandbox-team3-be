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

    public void checkDuplicateEmail(VendorRequestVO request) {
        if (request.getEmail() != null) {
            vendorRepository.findByEmail(request.getEmail())
                    .ifPresent(vendor -> {
                        throw new EntityAlreadyExistsException(Vendor.class, "email", vendor.getEmail());
                    });
        }
    }

    public void checkDuplicatePhones(VendorRequestVO request) {
        if (request.getPhoneNumber() != null) {
            vendorRepository.findByPhoneNumber(request.getPhoneNumber())
                    .ifPresent(vendor -> {
                        throw new EntityAlreadyExistsException(Vendor.class, "phone", vendor.getPhoneNumber());
                    });
        }
    }


    public void checkDuplicateEmailForUpdate(Vendor vendor, VendorRequestVO request) {
        if (vendor.getEmail() != null) {
            if (!vendor.getEmail().equals(request.getEmail())) {
                checkDuplicateEmail(request);
            }
        } else {
            checkDuplicateEmail(request);
        }
    }

    public void checkDuplicatePhonesForUpdate(Vendor vendor, VendorRequestVO request) {
        if (vendor.getPhoneNumber() != null) {
            if (!vendor.getPhoneNumber().equals(request.getPhoneNumber())) {
                checkDuplicatePhones(request);
            }
        } else {
            checkDuplicatePhones(request);
        }
    }
}