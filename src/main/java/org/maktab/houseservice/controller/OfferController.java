package org.maktab.houseservice.controller;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.offer.CreateOfferDto;
import org.maktab.houseservice.model.dto.offer.OfferDto;
import org.maktab.houseservice.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/offer")
public class OfferController {

    private final OfferService offerService;

    @PreAuthorize("hasAuthority('EXPERT')")
    @PostMapping("")
    public ResponseEntity<ApiResponse> createAnOffer(Principal principal, @RequestBody CreateOfferDto createOfferDto) {
        OfferDto offerDto = offerService.create(principal.getName(), createOfferDto);
        ApiResponse responseBody = SuccessApiResponse.withData(offerDto);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateOffer(Principal principal, @PathVariable Long id, @RequestBody CreateOfferDto createOfferDto) {
        OfferDto offerDto = offerService.updateOffer(principal.getName(), id, createOfferDto);
        ApiResponse responseBody = SuccessApiResponse.withData(offerDto);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('EXPERT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> removeOffer(Principal principal, @PathVariable Long id) {
        OfferDto offerDto = offerService.removeOffer(principal.getName(), id);
        ApiResponse responseBody = SuccessApiResponse.withData(offerDto);
        return ResponseEntity.ok(responseBody);
    }
}
