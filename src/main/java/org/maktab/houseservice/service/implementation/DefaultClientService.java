package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.exception.DuplicateEmailException;
import org.maktab.houseservice.model.dto.client.ClientRegistrationRequestDto;
import org.maktab.houseservice.model.dto.client.ClientResponseDto;
import org.maktab.houseservice.model.dto.expert.ExpertResponseDto;
import org.maktab.houseservice.model.entity.Client;
import org.maktab.houseservice.model.entity.ClientAccountStatus;
import org.maktab.houseservice.model.entity.Expert;
import org.maktab.houseservice.model.entity.ExpertAccountStatus;
import org.maktab.houseservice.repository.ClientRepository;
import org.maktab.houseservice.repository.UserRepository;
import org.maktab.houseservice.service.ClientService;
import org.maktab.houseservice.service.VerificationTokenService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultClientService implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;

    @Override
    @Transactional
    public ClientResponseDto signup(ClientRegistrationRequestDto clientDto) {
        Boolean clientAlreadyExists = userRepository.existsByEmail(clientDto.getEmail());
        if (clientAlreadyExists) {
            throw new DuplicateEmailException(String.format("email %s has been registered before", clientDto.getEmail()));
        }
        Client client = new Client(clientDto.getFirstName(), clientDto.getLastName(), clientDto.getEmail(), passwordEncoder.encode(clientDto.getPassword()), ClientAccountStatus.WAITING_FOR_EMAIL_CONFIRMATION, new HashSet<>(), 0L);
        clientRepository.save(client);
        verificationTokenService.sendVerificationTo(client);
        return ClientResponseDto.fromEntity(client);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("there is not user with email %s", email)));
    }

    @Transactional
    @Override
    public ClientResponseDto increaseBalance(String name, Long amount) {
        Client client = clientRepository.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("there is no client with id %d", name)));

        client.setBalance(client.getBalance() + amount);
        return ClientResponseDto.fromEntity(client);
    }

    @Transactional
    @Override
    public ClientResponseDto deleteAccount(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("email %s doesn't exists", email)));
        client.setRegistrationStatus(ClientAccountStatus.DELETED);
        client.setActive(false);
        clientRepository.save(client);
        return ClientResponseDto.fromEntity(client);
    }

    @Override
    public List<ClientResponseDto> search(String firstName, String lastName, String email) {
        Example<Client> clientExample = createExample(firstName, lastName, email);
        List<Client> matchedClients = clientRepository.findAll(clientExample);
        return matchedClients.stream().map(ClientResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClientResponseDto update(String clientEmail, ClientRegistrationRequestDto clientRegistrationRequestDto) {
        Client client = clientRepository.findByEmail(clientEmail).orElseThrow(() -> new UsernameNotFoundException(String.format("there is no client with id %s", clientEmail)));
        if (clientRegistrationRequestDto.getLastName() != null) {
            client.setFirstname(clientRegistrationRequestDto.getFirstName());
        }
        if (clientRegistrationRequestDto.getLastName() != null) {
            client.setLastname(clientRegistrationRequestDto.getLastName());
        }

        if (clientRegistrationRequestDto.getPassword() != null) {
            client.setPassword(passwordEncoder.encode(clientRegistrationRequestDto.getPassword()));

        }
        return ClientResponseDto.fromEntity(client);
    }


    private Example<Client> createExample(String firstName, String lastName, String email) {
        Client client = new Client();
        client.setFirstname(firstName);
        client.setLastname(lastName);
        client.setEmail(email);
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase("firstName", "lastName", "email")
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Client> clientExample = Example.of(client, matcher);
        return clientExample;
    }

}
