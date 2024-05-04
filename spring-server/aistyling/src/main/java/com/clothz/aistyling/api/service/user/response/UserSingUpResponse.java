package com.clothz.aistyling.api.service.user.response;

import com.clothz.aistyling.domain.user.User;
import lombok.Builder;

import java.util.List;

public record UserSingUpResponse(String email, String nickname, List<String> imgUrls) {
    @Builder
    public UserSingUpResponse(String email, String nickname, List<String> imgUrls) {
        this.email = email;
        this.nickname = nickname;
        this.imgUrls = imgUrls;
    }

    public static UserSingUpResponse of(User user, List<String> imgUrls) {
        return UserSingUpResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imgUrls(imgUrls)
                .build();
    }
}
