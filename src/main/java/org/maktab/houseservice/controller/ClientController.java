package org.maktab.houseservice.controller;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.client.ClientRegistrationRequestDto;
import org.maktab.houseservice.model.dto.client.ClientResponseDto;
import org.maktab.houseservice.model.dto.expert.ExpertResponseDto;
import org.maktab.houseservice.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.PositiveOrZero;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid ClientRegistrationRequestDto clientRegisterDto) {
        ClientResponseDto registeredClient = clientService.signup(clientRegisterDto);
        ApiResponse apiResponse = SuccessApiResponse.withMessageAndData(String.format("verification link sent to %s email", clientRegisterDto.getEmail()), registeredClient);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping
    public ResponseEntity<ApiResponse> update(Principal principal, @RequestBody @Valid ClientRegistrationRequestDto clientRegistrationRequestDto) {
        ClientResponseDto clientResponseDto = clientService.update(principal.getName(), clientRegistrationRequestDto);
        ApiResponse responseBody = SuccessApiResponse.withData(clientResponseDto);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping("/balance")
    public ResponseEntity<ApiResponse> increaseBalance(Principal principal, @RequestParam @Valid @PositiveOrZero Long amount) {
        ClientResponseDto clientResponseDto = clientService.increaseBalance(principal.getName(), amount);
        ApiResponse apiResponse = SuccessApiResponse.withData(clientResponseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAccount(Principal principal) {
        ClientResponseDto clientResponseDto = clientService.deleteAccount(principal.getName());
        ApiResponse apiResponse = SuccessApiResponse.withData(clientResponseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchExperts(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String email) {
        List<ClientResponseDto> experts = clientService.search(firstName, lastName, email);
        ApiResponse responseBody = SuccessApiResponse.withData(experts);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable @Valid @Email String email) {
        ClientResponseDto clientResponseDto = clientService.deleteAccount(email);
        ApiResponse apiResponse = SuccessApiResponse.withData(clientResponseDto);
        return ResponseEntity.ok(apiResponse);
    }
}
