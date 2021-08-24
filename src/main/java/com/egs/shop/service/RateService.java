package com.egs.shop.service;

import com.egs.shop.model.dto.RateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RateService {

    Page<RateDTO> getAllRatesByProductId(Long id, Pageable pageable);

    Page<RateDTO> getEnabledRatesByProductId(Long id, Pageable pageable);
}
