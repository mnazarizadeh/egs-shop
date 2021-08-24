package com.egs.shop.service.impl;

import com.egs.shop.exception.ProductNotFoundException;
import com.egs.shop.model.dto.RateDTO;
import com.egs.shop.model.mapper.RateMapper;
import com.egs.shop.repository.ProductRepository;
import com.egs.shop.repository.RateRepository;
import com.egs.shop.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RateServiceImpl implements RateService {

    private final ProductRepository productRepository;
    private final RateRepository rateRepository;
    private final RateMapper rateMapper;

    @Override
    public Page<RateDTO> getAllRatesByProductId(Long id, Pageable pageable) {
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        return rateRepository.findByProductId(id, pageable)
                .map(rateMapper::toDto);
    }

    @Override
    public Page<RateDTO> getEnabledRatesByProductId(Long id, Pageable pageable) {
        productRepository.findByIdAndEnabledIsTrue(id)
                .orElseThrow(ProductNotFoundException::new);

        return rateRepository.findByProductIdAndProductEnabledIsTrue(id, pageable)
                .map(rateMapper::toDto);
    }
}
