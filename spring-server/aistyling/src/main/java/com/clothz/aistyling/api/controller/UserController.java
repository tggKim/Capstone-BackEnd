package com.clothz.aistyling.api.controller;

import com.clothz.aistyling.api.ApiResponse;
import com.clothz.aistyling.api.controller.dto.request.UserCreateRequest;
import com.clothz.aistyling.api.service.user.UserService;
import com.clothz.aistyling.api.service.user.response.UserInfoResponse;
import com.clothz.aistyling.api.service.user.response.UserSingUpResponse;
import com.clothz.aistyling.global.jwt.userInfo.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserSingUpResponse> signUp(@RequestBody @Valid final UserCreateRequest request) {
        final var userResponse = userService.signUp(request);
        return ApiResponse.ok(userResponse);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<UserInfoResponse> getUserInfo(
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) throws JsonProcessingException {
        final var userResponse = userService.getUserInfo(userDetails.getId());
        return ApiResponse.ok(userResponse);
    }
}
