package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.mapper.DiscountMapper;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.exception.exception.client.OccurOptimisticLockException;
import com.exadel.discountwebapp.filter.SpecificationBuilder;
import com.exadel.discountwebapp.notification.event.EntityCreateEvent;
import com.exadel.discountwebapp.user.entity.User;
import com.exadel.discountwebapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountMapper discountMapper;
    private final DiscountRepository discountRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<DiscountResponseVO> findAll(String query, Pageable pageable) {
        SpecificationBuilder<Discount> specificationBuilder = new SpecificationBuilder<>();
        Specification<Discount> specification = specificationBuilder.fromQuery(query);

        Page<Discount> page = discountRepository.findAll(specification, pageable);
        return page.map(discountMapper::toVO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DiscountResponseVO findById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", id));
        while (true) {
            try {
                Long quantity = discount.getViewNumber() == null ? 1 : discount.getViewNumber() + 1;
                discount.setViewNumber(quantity);
                discount.setVersion(discount.getVersion());
                discountRepository.save(discount);
                break;
            } catch (OptimisticLockException ex) {
                throw new OccurOptimisticLockException("Some race condition happened");
            }
        }
        return discountMapper.toVO(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DiscountResponseVO create(DiscountRequestVO request) {
        Discount discount = discountMapper.toEntity(request);
        discountRepository.save(discount);

        EntityCreateEvent<Discount> event = new EntityCreateEvent<>(discount);
        eventPublisher.publishEvent(event);

        return discountMapper.toVO(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DiscountResponseVO update(Long id, DiscountRequestVO request) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", id));
        discountMapper.updateEntity(request, discount);
        discountRepository.save(discount);
        return discountMapper.toVO(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        discountRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addDiscountToFavorites(Long userId, Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", discountId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId));
        if (!discount.getUserFavorites().contains(user)) {
            discount.getUserFavorites().add(user);
            discountRepository.save(discount);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteDiscountFromFavorites(Long userId, Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", discountId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId));
        discount.getUserFavorites().remove(user);
        discountRepository.save(discount);
    }
}