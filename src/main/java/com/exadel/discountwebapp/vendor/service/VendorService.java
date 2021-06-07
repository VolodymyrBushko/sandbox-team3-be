package com.exadel.discountwebapp.vendor.service;

import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.mapper.VendorMapper;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VendorService {

    VendorMapper vendorMapper;
    VendorRepository vendorRepository;

    public List<VendorResponseVO> findAll() {
        System.out.println("Vendor Service" + vendorRepository);
        List<VendorResponseVO> response = new ArrayList<>();
        vendorRepository.findAll().forEach(en -> response.add(vendorMapper.toResponseVO(en)));
        return response;
    }

    public VendorResponseVO findById(Long id) {
        Vendor vendor = vendorRepository.findById(id).orElse(null);
        return vendor != null ? vendorMapper.toResponseVO(vendor) : null;
    }

    public VendorResponseVO findByTitle(String title) {
        Vendor vendor = vendorRepository.findByTitle(title);
        return vendor != null ? vendorMapper.toResponseVO(vendor) : null;
    }

    public VendorResponseVO create(VendorRequestVO request) {
        return vendorMapper.toResponseVO(vendorRepository.save(vendorMapper.toEntity(request)));
    }

    public VendorResponseVO update(Long id, VendorRequestVO request) {
        Vendor oldVendor = vendorRepository.findById(id).orElse(null);
        if (oldVendor != null) {
            Vendor newVendor = vendorMapper.toEntity(request);
            newVendor.setId(id);
            return vendorMapper.toResponseVO(vendorRepository.save(newVendor));
        }
        return null;
    }

    public void deleteById(Long id) {
        vendorRepository.deleteById(id);
    }
}
