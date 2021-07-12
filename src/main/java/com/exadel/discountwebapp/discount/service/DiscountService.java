package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.mapper.DiscountMapper;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.validator.DiscountValidator;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.filter.SpecificationBuilder;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountMapper discountMapper;
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;
    private final DiscountValidator discountValidator;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<DiscountResponseVO> findAll(String query, Pageable pageable) {
        SpecificationBuilder<Discount> specificationBuilder = new SpecificationBuilder<>();
        Specification<Discount> specification = specificationBuilder.fromQuery(query);

        Page<Discount> page = discountRepository.findAll(specification, pageable);
        return page.map(discountMapper::toVO);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public DiscountResponseVO findById(Long id) {
        discountValidator.checkIfDiscountIsPresentById(id);
        Discount discount = discountRepository.findById(id).get();
        return discountMapper.toVO(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DiscountResponseVO create(DiscountRequestVO request) {
        Discount discount = discountMapper.toEntity(request);
        discountRepository.save(discount);
        return discountMapper.toVO(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DiscountResponseVO update(Long id, DiscountRequestVO request) {
        discountValidator.checkIfDiscountIsPresentById(id);
        Discount discount = discountRepository.findById(id).get();
        discountMapper.updateEntity(request, discount);
        discountRepository.save(discount);
        return discountMapper.toVO(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        discountValidator.checkIfDiscountIsPresentById(id);
        discountRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> addDiscountToFavorites(Long userId, Long discountId) {
        discountValidator.checkIfDiscountIsPresentById(discountId);
        Discount discount = discountRepository.findById(discountId).get();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId));
        discountValidator.checkFavoritesToAdd(discount, user);
        discount.getFavoriteUsers().add(user);
        discountRepository.save(discount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> deleteDiscountFromFavorites(Long userId, Long discountId) {
        discountValidator.checkIfDiscountIsPresentById(discountId);
        Discount discount = discountRepository.findById(discountId).get();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId));
        discountValidator.checkFavoritesToDelete(discount, user);
        discount.getFavoriteUsers().remove(user);
        discountRepository.save(discount);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
