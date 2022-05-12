package org.maktab.houseservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.entity.AppUser;
import org.maktab.houseservice.model.entity.VerificationToken;
import org.maktab.houseservice.repository.VerificationTokenRepository;
import org.maktab.houseservice.service.EmailSenderService;
import org.maktab.houseservice.service.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultVerificationTokenService implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailSenderService emailSenderService;

    @Override
    public void sendVerificationTo(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(60);
        VerificationToken verificationToken = new VerificationToken(token, appUser, expireTime);
        verificationTokenRepository.save(verificationToken);
        String baseUrl = "http://localhost:8083/api/v1/user/verification/";
        emailSenderService.send(appUser.getEmail(), "verification", baseUrl.concat(token));
    }
}
