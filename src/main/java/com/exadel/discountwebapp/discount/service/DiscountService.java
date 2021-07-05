package com.exadel.discountwebapp.discount.service;

import com.exadel.discountwebapp.category.entity.Category;
import com.exadel.discountwebapp.discount.entity.Discount;
import com.exadel.discountwebapp.discount.mapper.DiscountMapper;
import com.exadel.discountwebapp.discount.repository.DiscountRepository;
import com.exadel.discountwebapp.discount.vo.DiscountRequestVO;
import com.exadel.discountwebapp.discount.vo.DiscountResponseVO;
import com.exadel.discountwebapp.exception.exception.client.EntityNotFoundException;
import com.exadel.discountwebapp.filter.SpecificationBuilder;
import com.exadel.discountwebapp.tag.entity.Tag;
import com.exadel.discountwebapp.tag.mapper.TagMapper;
import com.exadel.discountwebapp.tag.vo.TagResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DiscountService {

    private final TagMapper tagMapper;
    private final DiscountMapper discountMapper;
    private final DiscountRepository discountRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<DiscountResponseVO> findAll(String query, Pageable pageable) {
        SpecificationBuilder<Discount> specificationBuilder = new SpecificationBuilder<>();
        Specification<Discount> specification = specificationBuilder.fromQuery(query);

        Page<Discount> page = discountRepository.findAll(specification, pageable);
        return page.map(discountMapper::toVO);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public DiscountResponseVO findById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", id));
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
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", id));
        discountMapper.updateEntity(request, discount);
        discountRepository.save(discount);
        return discountMapper.toVO(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TagResponseVO> addTags(Long id, List<Long> tagIds) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Discount.class, "id", id));

        List<Tag> tags = discount.getCategory().getTags()
                .stream().filter(e -> tagIds.contains(e.getId())).collect(Collectors.toList());

        discount.getTags().addAll(tags);
        discountRepository.save(discount);

        return tags.stream().map(tagMapper::toVO).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        discountRepository.deleteById(id);
    }
}
