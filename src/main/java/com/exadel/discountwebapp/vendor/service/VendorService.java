package com.exadel.discountwebapp.vendor.service;

import com.exadel.discountwebapp.vendor.entity.Vendor;
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

    @Transactional(readOnly = true)
    public List<VendorResponseVO> findAll() {
        List<VendorResponseVO> response = new ArrayList<>();
        vendorRepository.findAll().forEach(en -> response.add(vendorMapper.toResponseVO(en)));
        return response;
    }

    @Transactional(readOnly = true)
    public VendorResponseVO findById(Long id) {
        Vendor vendor = vendorRepository.findById(id).orElse(null);
        return vendor != null ? vendorMapper.toResponseVO(vendor) : null;
    }

    @Transactional(readOnly = true)
    public VendorResponseVO findByTitle(String title) {
        Optional<Vendor> vendor = vendorRepository.findByTitle(title);
        return vendor.map(vendorMapper::toResponseVO).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VendorResponseVO create(VendorRequestVO request) {
        return vendorMapper.toResponseVO(vendorRepository.save(vendorMapper.toEntity(request)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VendorResponseVO update(Long id, VendorRequestVO request) {
        Vendor vendor = vendorRepository.findById(id).orElse(null);
        Vendor updatedVendor = vendorMapper.updateVO(vendor, request);
        return vendorMapper.toResponseVO(vendorRepository.save(updatedVendor));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        vendorRepository.deleteById(id);
    }
}
