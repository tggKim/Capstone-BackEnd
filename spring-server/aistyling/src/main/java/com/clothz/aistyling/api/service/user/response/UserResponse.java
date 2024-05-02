package com.clothz.aistyling.api.service.user.response;

import com.clothz.aistyling.domain.user.User;
import lombok.Builder;

public record UserResponse(String nickname) {
    @Builder
    public UserResponse(String nickname){
        this.nickname = nickname;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .nickname(user.getEmail())
                .build();
    }
}
