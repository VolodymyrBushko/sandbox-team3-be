package com.exadel.discountwebapp.vendor.service;

import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.exception.VendorNotFoundException;
import com.exadel.discountwebapp.vendor.mapper.VendorMapper;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<VendorResponseVO> findAll() {
        List<VendorResponseVO> response = new ArrayList<>();
        vendorRepository.findAll().forEach(en -> response.add(vendorMapper.toVO(en)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public VendorResponseVO findById(Long id) {
        Optional<Vendor> vendor = Optional.ofNullable(vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException("Could not find vendor with id: " + id)));
        return vendor.map(vendorMapper::toVO).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public VendorResponseVO findByTitle(String title) {
        Optional<Vendor> vendor = Optional.ofNullable(vendorRepository.findByTitle(title)
                .orElseThrow(() -> new VendorNotFoundException("Could not find vendor with title: " + title)));
        return vendor.map(vendorMapper::toVO).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VendorResponseVO create(VendorRequestVO request) {
        return vendorMapper.toVO(vendorRepository.save(vendorMapper.toEntity(request)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VendorResponseVO update(Long id, VendorRequestVO request) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException("Could not find vendor with id: " + id));
        Vendor updatedVendor = vendorMapper.updateVO(vendor, request);
        return vendorMapper.toVO(vendorRepository.save(updatedVendor));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        vendorRepository.deleteById(id);
    }
}
