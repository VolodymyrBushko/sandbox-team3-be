package com.exadel.discountwebapp.vendor.service;

import com.exadel.discountwebapp.baseclasses.BaseEntityMapper;
import com.exadel.discountwebapp.baseclasses.BaseFilterService;
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
        var vendor = getVendorById(id);
        if (!vendor.getEmail().equals(request.getEmail())) {
            vendorEmailValidator.validate(request);
        }
        vendorMapper.update(request, vendor);
        var updatedVendor = vendorRepository.save(vendor);
        return vendorMapper.toVO(updatedVendor);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        vendorRepository.deleteById(id);
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
