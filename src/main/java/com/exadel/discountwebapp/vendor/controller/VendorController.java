package com.exadel.discountwebapp.vendor.controller;

import com.exadel.discountwebapp.vendor.service.VendorService;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@AllArgsConstructor
public class VendorController {
    private VendorService vendorService;

    @GetMapping
    public List<VendorResponseVO> findAllVendors() {
        return vendorService.findAll();
    }

    @GetMapping("/{id}")
    public VendorResponseVO findById(@PathVariable Long id) {
        return vendorService.findById(id);
    }

    @GetMapping("/title")
    public VendorResponseVO findByTitle(@RequestParam(name="title") String title) {
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
