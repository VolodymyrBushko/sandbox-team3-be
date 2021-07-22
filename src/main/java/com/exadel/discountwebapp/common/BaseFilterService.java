package com.exadel.discountwebapp.common;

import com.exadel.discountwebapp.filter.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public abstract class BaseFilterService<ENTITY, RESPONSE_VO> {

    protected abstract JpaSpecificationExecutor<ENTITY> getEntityRepository();

    protected abstract BaseEntityMapper<ENTITY, RESPONSE_VO> getEntityToVOMapper();

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<RESPONSE_VO> findAll(String query, Pageable pageable) {
        SpecificationBuilder<ENTITY> specificationBuilder = new SpecificationBuilder<>();
        Specification<ENTITY> specification = specificationBuilder.fromQuery(query);
        Page<ENTITY> page = getEntityRepository().findAll(specification, pageable);
        return page.map(getEntityToVOMapper()::toVO);
    }
}