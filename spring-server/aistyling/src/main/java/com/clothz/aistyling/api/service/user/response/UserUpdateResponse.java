package com.clothz.aistyling.api.service.user.response;


import lombok.Builder;
public record UserUpdateResponse(String email, String nickname, String password) {

    @Builder
    public UserUpdateResponse(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}


