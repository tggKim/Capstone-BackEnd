package com.clothz.aistyling.api.controller.user;

import com.clothz.aistyling.api.ApiResponse;
import com.clothz.aistyling.api.controller.user.request.UserCreateRequest;
import com.clothz.aistyling.api.service.user.UserService;
import com.clothz.aistyling.api.service.user.response.UserImagesResponse;
import com.clothz.aistyling.api.service.user.response.UserInfoResponse;
import com.clothz.aistyling.api.service.user.response.UserSingUpResponse;
import com.clothz.aistyling.global.jwt.userInfo.CustomUserDetails;
import com.clothz.aistyling.global.s3.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;
    private final S3Service s3Service;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<UserSingUpResponse> signUp(
            @RequestBody @Valid final UserCreateRequest request,
            final List<MultipartFile> images) throws IOException {
        final var imgUrls = s3Service.upload(images);
        final var userSingUpResponse = userService.signUp(request, imgUrls);
        return ApiResponse.ok(userSingUpResponse);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<UserInfoResponse> getUserInfo(
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) throws JsonProcessingException {
        final var userInfoResponse = userService.getUserInfo(userDetails.getId());
        return ApiResponse.ok(userInfoResponse);
    }

    @PostMapping(value = "/users/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<UserImagesResponse> uploadUserImg(
            final List<MultipartFile> images,
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) throws IOException {
        final var imgUrls = s3Service.upload(images);
        final var userImagesResponse = userService.uploadUserImg(imgUrls, userDetails.getId());
        return ApiResponse.ok(userImagesResponse);
    }
}
