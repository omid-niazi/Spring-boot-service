package org.maktab.houseservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;
import org.maktab.houseservice.model.entity.Client;
import org.maktab.houseservice.model.entity.Request;
import org.maktab.houseservice.model.entity.RequestStatus;
import org.maktab.houseservice.model.entity.Service;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class RequestCreationDto {
    private String title;
    private String description;
    private Long offerAmount;
    private LocalDateTime doingTime;
    private Long serviceId;

    public Request toEntity() {
        return new Request(null, RequestStatus.WAITING_FOR_CONFIRM, getOfferAmount(), getTitle(), getDescription(), getDoingTime(), null);
    }
}
