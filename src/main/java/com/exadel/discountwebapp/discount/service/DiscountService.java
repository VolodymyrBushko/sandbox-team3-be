package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.mapper.DiscountMapper;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountMapper discountMapper;
    private final DiscountRepository discountRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<DiscountResponseVO> findAll() {
        List<DiscountResponseVO> response = new ArrayList<>();
        discountRepository.findAll().forEach(e -> response.add(discountMapper.toVO(e)));
        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public DiscountResponseVO findById(Long id) {
        Discount discount = discountRepository.findById(id).orElse(null);
        return discount != null ? discountMapper.toVO(discount) : null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DiscountResponseVO create(DiscountRequestVO request) {
        Discount discount = discountMapper.toEntity(request);
        return discountMapper.toVO(discountRepository.save(discount));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DiscountResponseVO update(Long id, DiscountRequestVO request) {
        Discount oldDiscount = discountRepository.findById(id).orElse(null);
        if (oldDiscount != null) {
            Discount newDiscount = discountMapper.toEntity(request);
            newDiscount.setId(id);
            return discountMapper.toVO(discountRepository.save(newDiscount));
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        discountRepository.deleteById(id);
    }
}
