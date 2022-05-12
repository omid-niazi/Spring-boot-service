package org.maktab.houseservice.service.implementation;

import org.maktab.houseservice.exception.InvalidRequestException;
import org.maktab.houseservice.model.dto.user.UserLoginResponseDto;
import org.maktab.houseservice.model.dto.user.UserLoginRequestDto;
import org.maktab.houseservice.model.entity.*;
import org.maktab.houseservice.repository.UserRepository;
import org.maktab.houseservice.repository.VerificationTokenRepository;
import org.maktab.houseservice.security.JwtUtil;
import org.maktab.houseservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    public DefaultUserService(UserRepository userRepository, JwtUtil jwtUtil, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Autowired
    public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user %s does not exists", username)));
        return new User(appUser.getEmail(), appUser.getPassword(), appUser.getActive(), true, true, true, List.of(new SimpleGrantedAuthority(appUser.getUserRole().name())));
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()));
        UserDetails userDetails = loadUserByUsername(userLoginRequestDto.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return new UserLoginResponseDto(token);
    }

    @Transactional
    @Override
    public void confirmEmail(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findById(token).orElseThrow(() -> new InvalidRequestException("token is not valid"));
        if (LocalDateTime.now().isAfter(verificationToken.getExpireDate())) {
            throw new InvalidRequestException("token is expired");
        }

        UserRole userRole = verificationToken.getAppUser().getUserRole();
        if (userRole == UserRole.CLIENT) {
            Client client = (Client) verificationToken.getAppUser();
            client.setActive(true);
            client.setRegistrationStatus(ClientAccountStatus.ACTIVE);
        } else if (userRole == UserRole.EXPERT) {
            Expert expert = (Expert) verificationToken.getAppUser();
            expert.setActive(true);
            expert.setAccountStatus(ExpertAccountStatus.ACTIVE);
        }
    }
}
