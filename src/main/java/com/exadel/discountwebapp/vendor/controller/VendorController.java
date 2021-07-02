package com.exadel.discountwebapp.vendor.controller;

import com.exadel.discountwebapp.vendor.service.VendorService;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @GetMapping
    public Page<VendorResponseVO> findAllVendors(@RequestParam(value = "query", defaultValue = "", required = false) String query,
                                                 @PageableDefault(sort = {"title"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return vendorService.findAll(query, pageable);
    }

    @GetMapping("/{id}")
    public VendorResponseVO findById(@PathVariable Long id) {
        return vendorService.findById(id);
    }

    @GetMapping("/title")
    public VendorResponseVO findByTitle(@Valid @RequestParam(name = "title") String title) {
        return vendorService.findByTitle(title);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public VendorResponseVO create(@Valid @RequestBody VendorRequestVO request) {
        return vendorService.create(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public VendorResponseVO update(@PathVariable Long id, @Valid @RequestBody VendorRequestVO request) {
        return vendorService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vendorService.deleteById(id);
    }
}
