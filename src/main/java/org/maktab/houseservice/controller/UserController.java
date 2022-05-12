package org.maktab.houseservice.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.SuccessApiResponse;
import org.maktab.houseservice.model.dto.user.UserLoginResponseDto;
import org.maktab.houseservice.model.dto.user.UserLoginRequestDto;
import org.maktab.houseservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        ApiResponse responseBody = SuccessApiResponse.withMessageAndData("send token in Authorization header for authentication", userLoginResponseDto);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<ApiResponse> verifyEmail(@PathVariable @Valid @NotNull String token) {
        userService.confirmEmail(token);
        ApiResponse responseBody = SuccessApiResponse.withMessage("your account activated");
        return ResponseEntity.ok(responseBody);
    }

}
