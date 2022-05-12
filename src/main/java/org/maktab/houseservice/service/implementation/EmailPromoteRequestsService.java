package org.maktab.houseservice.service.implementation;

import org.maktab.houseservice.service.PromoteRequestsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailPromoteRequestsService implements PromoteRequestsService {

    @Override
    public void promoteRequests() {
        System.out.println("test");
    }
}
