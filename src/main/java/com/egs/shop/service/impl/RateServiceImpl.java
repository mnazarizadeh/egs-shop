package com.egs.shop.service.impl;

import com.egs.shop.exception.*;
import com.egs.shop.model.Product;
import com.egs.shop.model.Rate;
import com.egs.shop.model.User;
import com.egs.shop.model.dto.RateDTO;
import com.egs.shop.model.mapper.RateMapper;
import com.egs.shop.repository.ProductRepository;
import com.egs.shop.repository.RateRepository;
import com.egs.shop.repository.UserRepository;
import com.egs.shop.security.SecurityUtils;
import com.egs.shop.service.ProductService;
import com.egs.shop.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RateServiceImpl implements RateService {

    private final ProductService productService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RateRepository rateRepository;
    private final RateMapper rateMapper;

    @Override
    public RateDTO createRate(RateDTO rateDTO) {
        Product product = productRepository.findById(rateDTO.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findByUsername(
                SecurityUtils.getCurrentUserUsername()
                        .orElseThrow(AuthenticatedUserNotFoundException::new))
                .orElseThrow(UserNotFoundException::new);

        // check for previous comments for this item
        rateRepository.findByUserAndProduct(user,product)
                .ifPresent(comment -> {
                    throw new RateAlreadyExistsException();
                });

        Rate newRate = rateMapper.toEntity(rateDTO);
        newRate.setProduct(product);
        newRate.setUser(user);
        newRate.setCreateDate(LocalDateTime.now());

        RateDTO result = rateMapper.toDto(rateRepository.save(newRate));

        result.setProduct(productService.updateRatesInfo(newRate.getProduct().getId()));
        return result;
    }

    @Override
    public RateDTO updateRate(RateDTO rateDTO) {
        Rate rate = rateRepository.findById(rateDTO.getId())
                .orElseThrow(CommentNotFoundException::new);

        checkUserAccess(rate);

        rate.setPoint(rateDTO.getPoint());
        RateDTO result = rateMapper.toDto(rateRepository.save(rate));

        result.setProduct(productService.updateRatesInfo(rate.getProduct().getId()));
        return result;
    }

    @Override
    public Page<RateDTO> getAllRates(Pageable pageable) {
        return rateRepository.findAll(pageable)
                .map(rateMapper::toDto);
    }

    @Override
    public Page<RateDTO> getMyRates(Pageable pageable) {
        String authenticatedUsername = SecurityUtils.getCurrentUserUsername()
                .orElseThrow(AuthenticatedUserNotFoundException::new);

        return rateRepository.findByUserUsername(authenticatedUsername, pageable)
                .map(rateMapper::toDto);
    }

    @Override
    public RateDTO getRate(Long id) {
        return rateRepository.findById(id)
                .map(rateMapper::toDto)
                .orElseThrow(RateNotFoundException::new);
    }

    @Override
    public RateDTO getMyRate(Long id) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(RateNotFoundException::new);

        checkUserAccess(rate);

        return rateMapper.toDto(rate);
    }

    @Override
    public void deleteById(Long id) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(RateNotFoundException::new);
        rateRepository.deleteById(id);
        productService.updateRatesInfo(rate.getProduct().getId());
    }

    @Override
    public void deleteMineById(Long id) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(RateNotFoundException::new);
        checkUserAccess(rate);
        rateRepository.deleteById(id);
        productService.updateRatesInfo(rate.getProduct().getId());
    }

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

    private void checkUserAccess(Rate rate) {
        if (!rate.getUser().getUsername().equals(
                SecurityUtils.getCurrentUserUsername()
                        .orElseThrow(AuthenticatedUserNotFoundException::new))) {
            throw new RateAccessDeniedException();
        }
    }
}
