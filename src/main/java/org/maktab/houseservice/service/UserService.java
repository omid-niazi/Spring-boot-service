package org.maktab.houseservice.service;

import org.maktab.houseservice.model.dto.user.UserLoginResponseDto;
import org.maktab.houseservice.model.dto.user.UserLoginRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);

    void confirmEmail(String token);
}
