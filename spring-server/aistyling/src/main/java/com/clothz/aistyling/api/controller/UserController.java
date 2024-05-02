package com.clothz.aistyling.api.controller;

import com.clothz.aistyling.api.ApiResponse;
import com.clothz.aistyling.api.controller.dto.request.UserCreateRequest;
import com.clothz.aistyling.api.service.user.UserService;
import com.clothz.aistyling.api.service.user.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResponse> signUp(@RequestBody @Valid final UserCreateRequest request) {
        var userResponse = userService.signUp(request);
        return ApiResponse.ok(userResponse);
    }
}
