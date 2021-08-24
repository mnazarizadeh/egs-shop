package com.egs.shop.service;

import com.egs.shop.model.dto.RateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RateService {

    RateDTO createRate(RateDTO rateDTO);

    RateDTO updateRate(RateDTO rateDTO);

    Page<RateDTO> getAllRates(Pageable pageable);

    Page<RateDTO> getMyRates(Pageable pageable);

    RateDTO getRate(Long id);

    RateDTO getMyRate(Long id);

    void deleteById(Long id);

    void deleteMineById(Long id);

    Page<RateDTO> getAllRatesByProductId(Long id, Pageable pageable);

    Page<RateDTO> getEnabledRatesByProductId(Long id, Pageable pageable);

}
