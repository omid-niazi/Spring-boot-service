package org.maktab.houseservice.controller;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.request.RequestResponseDto;
import org.maktab.houseservice.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.maktab.houseservice.model.dto.request.RequestCreationDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/request")
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRequests(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> itemsInPage) {
        List<RequestResponseDto> requestDtoList = requestService.findAll(page, itemsInPage);
        ApiResponse responseBody = SuccessApiResponse.withData(requestDtoList);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getRequestById(@PathVariable Long id) {
        RequestResponseDto requestDto = requestService.findById(id);
        ApiResponse responseBody = SuccessApiResponse.withData(requestDto);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping
    public ResponseEntity<ApiResponse> createRequest(Principal principal, @RequestBody @Valid RequestCreationDto requestCreationDto) {
        RequestResponseDto requestDto = requestService.createRequest(principal.getName(), requestCreationDto);
        ApiResponse responseBody = SuccessApiResponse.withData(requestDto);
        return ResponseEntity.ok(responseBody);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/confirm/{id}")
    public ResponseEntity<ApiResponse> confirmRequest(@PathVariable("id") Long requestId) {
        RequestResponseDto requestDto = requestService.confirmRequest(requestId);
        ApiResponse responseBody = SuccessApiResponse.withData(requestDto);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping("choose-offer/{offerId}")
    public ResponseEntity<ApiResponse> chooseOffer(Principal principal, @PathVariable("offerId") Long offerId, @RequestParam Long requestId) {
        RequestResponseDto requestDto = requestService.chooseOffer(principal.getName(), offerId, requestId);
        ApiResponse responseBody = SuccessApiResponse.withData(requestDto);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping("/done/{id}")
    public ResponseEntity<ApiResponse> doneRequest(Principal principal, @PathVariable Long id) {
        RequestResponseDto requestDto = requestService.doneRequest(principal.getName(), id);
        ApiResponse responseBody = SuccessApiResponse.withData(requestDto);
        return ResponseEntity.ok(responseBody);
    }
}
