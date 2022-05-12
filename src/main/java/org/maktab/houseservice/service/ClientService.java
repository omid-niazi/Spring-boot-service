package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.client.ClientRegistrationRequestDto;
import org.maktab.houseservice.model.dto.client.ClientResponseDto;
import org.maktab.houseservice.model.entity.Client;

import java.security.Principal;
import java.util.List;

public interface ClientService {
    ClientResponseDto signup(ClientRegistrationRequestDto clientRegistrationDto);

    Client findByEmail(String email);

    ClientResponseDto increaseBalance(String name, Long amount);

    ClientResponseDto deleteAccount(String email);

    List<ClientResponseDto> search(String firstName, String lastName, String email);

    ClientResponseDto update(String clientEmail, ClientRegistrationRequestDto clientRegistrationRequestDto);
}
