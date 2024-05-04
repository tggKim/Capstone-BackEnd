package com.clothz.aistyling.api.controller.styling;

import com.clothz.aistyling.api.ApiResponse;
import com.clothz.aistyling.api.service.styling.StylingService;
import com.clothz.aistyling.api.service.styling.response.StylingExampleResponse;
import com.clothz.aistyling.global.jwt.userInfo.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class StylingController {
    private final StylingService stylingService;
    @GetMapping("/styling")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse<List<StylingExampleResponse>> getUserInfo(
            @AuthenticationPrincipal final CustomUserDetails userDetails){
        final var imageAndPrompt = stylingService.getImageAndPrompt();
        return ApiResponse.ok(imageAndPrompt);
    }
}
