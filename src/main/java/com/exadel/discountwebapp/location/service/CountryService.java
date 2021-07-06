package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.location.mapper.CountryMapper;
import com.exadel.discountwebapp.location.repository.CountryRepository;
import com.exadel.discountwebapp.location.vo.country.CountryResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<CountryResponseVO> findAllCountries() {
        return countryRepository.findAll().stream().map(countryMapper::toVO).collect(Collectors.toList());
    }
}
