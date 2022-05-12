package org.maktab.houseservice.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maktab.houseservice.model.dto.request.RequestResponseDto;
import org.maktab.houseservice.model.entity.Client;
import org.maktab.houseservice.model.entity.ClientAccountStatus;

import javax.print.attribute.HashAttributeSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class ClientResponseDto {
    private String firstname;
    private String lastname;
    private String email;
    private ClientAccountStatus clientAccountStatus;
    private Long balance;
    private Set<RequestResponseDto> requestSet;

    public static ClientResponseDto fromEntity(Client client) {
        Set<RequestResponseDto> requests = client.getRequests()
                .stream().map(RequestResponseDto::fromEntity)
                .collect(Collectors.toSet());
        return new ClientResponseDto(client.getFirstname(), client.getLastname(), client.getEmail(), client.getRegistrationStatus(), client.getBalance(), requests);
    }

    public static ClientResponseDto fromEntity(Client client, boolean showRequests) {
        return new ClientResponseDto(client.getFirstname(), client.getLastname(), client.getEmail(), client.getRegistrationStatus(), client.getBalance(), showRequests ?
                client.getRequests()
                        .stream().map(RequestResponseDto::fromEntity)
                        .collect(Collectors.toSet()) : new HashSet<>());
    }
}
