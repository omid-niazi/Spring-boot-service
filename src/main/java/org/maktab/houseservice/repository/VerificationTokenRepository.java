package org.maktab.houseservice.repository;

import org.maktab.houseservice.model.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.web.authentication.preauth.j2ee.J2eePreAuthenticatedProcessingFilter;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
}
