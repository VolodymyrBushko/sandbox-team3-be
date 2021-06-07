package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public List<DiscountResponseVO> findAll() {
        List<DiscountResponseVO> response = new ArrayList<>();
        discountRepository.findAll().forEach(e -> response.add(DiscountResponseVO.fromDiscount(e)));
        return response;
    }

    public DiscountResponseVO findById(long id) {
        Discount discount = discountRepository.findById(id).orElse(null);
        return discount != null ? DiscountResponseVO.fromDiscount(discount) : null;
    }

    public DiscountResponseVO create(DiscountRequestVO request) {
        Discount discount = DiscountRequestVO.toDiscount(request);
        return DiscountResponseVO.fromDiscount(discountRepository.save(discount));
    }

    public DiscountResponseVO update(long id, DiscountRequestVO request) {
        Discount oldDiscount = discountRepository.findById(id).orElse(null);
        if (oldDiscount != null) {
            Discount newDiscount = DiscountRequestVO.toDiscount(request);
            newDiscount.setId(id);
            return DiscountResponseVO.fromDiscount(discountRepository.save(newDiscount));
        }
        return null;
    }

    public void deleteById(long id) {
        discountRepository.deleteById(id);
    }
}
