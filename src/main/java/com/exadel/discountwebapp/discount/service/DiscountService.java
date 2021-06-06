package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.RequestDiscountVO;
import com.exadel.discountwebapp.discount.vo.ResponseDiscountVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public List<ResponseDiscountVO> findAll() {
        List<ResponseDiscountVO> response = new ArrayList<>();
        discountRepository.findAll().forEach(e -> response.add(ResponseDiscountVO.fromDiscount(e)));
        return response;
    }

    public ResponseDiscountVO findById(long id) {
        Discount discount = discountRepository.findById(id).orElse(null);
        return discount != null ? ResponseDiscountVO.fromDiscount(discount) : null;
    }

    public ResponseDiscountVO create(RequestDiscountVO request) {
        Discount discount = RequestDiscountVO.toDiscount(request);
        return ResponseDiscountVO.fromDiscount(discountRepository.save(discount));
    }

    public ResponseDiscountVO update(long id, RequestDiscountVO request) {
        Discount oldDiscount = discountRepository.findById(id).orElse(null);
        if (oldDiscount != null) {
            Discount newDiscount = RequestDiscountVO.toDiscount(request);
            newDiscount.setId(id);
            return ResponseDiscountVO.fromDiscount(discountRepository.save(newDiscount));
        }
        return null;
    }

    public void deleteById(long id) {
        discountRepository.deleteById(id);
    }
}
