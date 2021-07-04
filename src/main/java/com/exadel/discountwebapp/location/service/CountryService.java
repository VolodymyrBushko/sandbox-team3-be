package com.exadel.discountwebapp.location.service;

import com.exadel.discountwebapp.location.entity.Country;
import com.exadel.discountwebapp.location.mapper.CountryMapper;
import com.exadel.discountwebapp.location.repository.CountryRepository;
import com.exadel.discountwebapp.location.vo.country.CountryResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public List<CountryResponseVO> findAllCountries() {
        List<Country> countries = countryRepository.findAll();
        List<CountryResponseVO> response = new ArrayList<>();
        countries.forEach(entity -> response.add(countryMapper.toVO(entity)));
        return response;
    }
}
