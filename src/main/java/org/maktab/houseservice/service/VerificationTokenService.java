package org.maktab.houseservice.service;

import org.maktab.houseservice.model.entity.AppUser;

public interface VerificationTokenService {
    void sendVerificationTo(AppUser appUser);
}
