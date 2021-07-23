package com.exadel.discountwebapp.vendor.validator;

import com.exadel.discountwebapp.exception.exception.client.EntityAlreadyExistsException;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class VendorValidator {

    private final VendorRepository vendorRepository;

    public void validateForCreate(VendorRequestVO request) {
        checkDuplicateEmail(request);
        checkDuplicatePhones(request);
    }

    public void validateForUpdate(Vendor vendor, VendorRequestVO request) {
        checkDuplicateEmailForUpdate(vendor, request);
        checkDuplicatePhonesForUpdate(vendor, request);
    }

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
        if (!Objects.equals(vendor.getEmail(), request.getEmail())) {
            checkDuplicateEmail(request);
        }
    }

    public void checkDuplicatePhonesForUpdate(Vendor vendor, VendorRequestVO request) {
        if (!Objects.equals(vendor.getPhoneNumber(), request.getPhoneNumber())) {
            checkDuplicatePhones(request);
        }
    }
}