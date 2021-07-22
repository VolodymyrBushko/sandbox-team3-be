package com.exadel.discountwebapp.vendor.service;

import com.exadel.discountwebapp.common.BaseEntityMapper;
import com.exadel.discountwebapp.common.BaseFilterService;
import com.exadel.discountwebapp.fileupload.image.ImageUploadService;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.service.UserService;
import com.exadel.discountwebapp.vendor.entity.Vendor;
import com.exadel.discountwebapp.vendor.mapper.VendorMapper;
import com.exadel.discountwebapp.vendor.repository.VendorRepository;
import com.exadel.discountwebapp.vendor.validator.VendorEmailValidator;
import com.exadel.discountwebapp.vendor.vo.VendorRequestVO;
import com.exadel.discountwebapp.vendor.vo.VendorResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorService extends BaseFilterService<Vendor, VendorResponseVO> {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;
    private final VendorEmailValidator vendorEmailValidator;
    private final UserService userService;
    private final ImageUploadService imageUploadService;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public VendorResponseVO findById(Long id) {
        return vendorMapper.toVO(getVendorById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public VendorResponseVO findByTitle(String title) {
        Optional<Vendor> vendor = Optional.ofNullable(vendorRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException(Vendor.class, "title", title)));
        return vendor.map(vendorMapper::toVO).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VendorResponseVO create(VendorRequestVO request) {
        vendorEmailValidator.validate(request);
        return vendorMapper.toVO(vendorRepository.save(vendorMapper.toEntity(request)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public VendorResponseVO update(Long id, VendorRequestVO request) {
        Vendor vendor = getVendorById(id);
        String imageUrl = vendor.getImageUrl();

        if (!vendor.getEmail().equals(request.getEmail())) {
            vendorEmailValidator.validate(request);
        }

        vendorMapper.update(request, vendor);
        Vendor updatedVendor = vendorRepository.save(vendor);

        if (imageUrl != null && !imageUrl.equals(request.getImageUrl())) {
            imageUploadService.delete(imageUrl);
        }

        return vendorMapper.toVO(updatedVendor);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        Vendor vendor = getVendorById(id);
        vendorRepository.deleteById(vendor.getId());
        imageUploadService.delete(vendor.getImageUrl());
    }

    private Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Vendor.class, "id", id));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void subscribe(Long vendorId, String userEmail) {
        Vendor vendor = getVendorById(vendorId);
        List<User> subscribers = vendor.getSubscribers();
        User user = userService.getUserByEmail(userEmail);

        if (!subscribers.contains(user)) {
            subscribers.add(user);
            vendorRepository.save(vendor);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void unsubscribe(Long vendorId, String userEmail) {
        Vendor vendor = getVendorById(vendorId);
        User user = userService.getUserByEmail(userEmail);

        boolean isRemoved = vendor.getSubscribers().remove(user);

        if (isRemoved) {
            vendorRepository.save(vendor);
        }
    }

    @Override
    protected JpaSpecificationExecutor<Vendor> getEntityRepository() {
        return vendorRepository;
    }

    @Override
    protected BaseEntityMapper<Vendor, VendorResponseVO> getEntityToVOMapper() {
        return vendorMapper;
    }
}
