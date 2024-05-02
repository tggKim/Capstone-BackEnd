package com.clothz.aistyling.api.service.user.response;

import com.clothz.aistyling.domain.user.User;
import lombok.Builder;

public record UserResponse(String email, String nickname) {
    @Builder
    public UserResponse(String email, String nickname){
        this.email = email;
        this.nickname = nickname;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
