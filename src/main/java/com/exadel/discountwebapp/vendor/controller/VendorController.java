package com.exadel.discountwebapp.vendor.controller;

import com.exadel.discountwebapp.vendor.service.VendorService;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
@AllArgsConstructor
public class VendorController {
    VendorService vendorService;

    @GetMapping
    public List<VendorResponseVO> findAllVendors() {
        System.out.println("Vendor controller" + vendorService);
        return vendorService.findAll();
    }

    @GetMapping("/{id}")

    public VendorResponseVO findById(@PathVariable Long id) {
        System.out.println("search by ID" + vendorService);
        return vendorService.findById(id);
    }

    @GetMapping("/title/{title}")
    public VendorResponseVO findByTitle(@PathVariable String title) {
        System.out.println("search by TITLE" + vendorService);
        return vendorService.findByTitle(title);
    }

    @PostMapping
    public VendorResponseVO save(@RequestBody VendorRequestVO request) {
        return vendorService.create(request);
    }

    @PutMapping("/{id}")
    public VendorResponseVO update(@PathVariable Long id, @RequestBody VendorRequestVO request) {
        return vendorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vendorService.deleteById(id);
    }
}
