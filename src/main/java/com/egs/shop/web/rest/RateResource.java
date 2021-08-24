package com.egs.shop.web.rest;

import com.egs.shop.model.dto.RateDTO;
import com.egs.shop.service.RateService;
import com.egs.shop.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RateResource {

    private final RateService rateService;

    @PostMapping("/rates")
    public ResponseEntity<RateDTO> createRate(@Valid @RequestBody RateDTO rate) throws URISyntaxException {
        log.debug("REST request to make a rate on a product : {}", rate);

        RateDTO result = rateService.createRate(rate);
        return ResponseEntity
                .created(new URI("/api/rate/" + result.getId()))
                .body(result);
    }

    @PutMapping("/rates/{id}")
    public ResponseEntity<RateDTO> updateRate(@PathVariable(value = "id") final Long id,
                                                    @Valid @RequestBody RateDTO rate) throws URISyntaxException {
        log.debug("REST request to update Rate : {}, {}", id, rate);

        rate.setId(id);
        RateDTO result = rateService.updateRate(rate);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/admin/rates")
    public ResponseEntity<List<RateDTO>> getAllComments(Pageable pageable) {
        log.debug("REST request to get a page of Rates");

        Page<RateDTO> page = rateService.getAllRates(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    @GetMapping("/rates")
    public ResponseEntity<List<RateDTO>> getMyRates(Pageable pageable) {
        log.debug("REST request to get a page of user Rates");

        Page<RateDTO> page = rateService.getMyRates(pageable);
        return ResponseEntity.ok()
                .headers(PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page))
                .body(page.getContent());
    }

    @GetMapping("/admin/rates/{id}")
    public ResponseEntity<RateDTO> getRate(@PathVariable Long id) {
        log.debug("REST request to get Rate id {} by ADMIN", id);

        RateDTO result = rateService.getRate(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/rates/{id}")
    public ResponseEntity<RateDTO> getMyRate(@PathVariable Long id) {
        log.debug("REST request to get Rate id {} by USER", id);

        RateDTO result = rateService.getMyRate(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/admin/rates/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        log.debug("REST request to delete Rate id {} by ADMIN", id);

        rateService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/rates/{id}")
    public ResponseEntity<Void> deleteMyRate(@PathVariable Long id) {
        log.debug("REST request to delete Rate id {} by USER", id);

        rateService.deleteMineById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
