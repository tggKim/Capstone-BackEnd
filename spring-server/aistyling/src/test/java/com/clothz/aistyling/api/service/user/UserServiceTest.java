package com.clothz.aistyling.api.service.user;

import com.clothz.aistyling.api.controller.dto.request.UserCreateRequest;
import com.clothz.aistyling.api.service.user.response.UserResponse;
import com.clothz.aistyling.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {
    private static final String EMAIL = "user12@gmail.com";
    private static final String PASSWORD = "password1!";
    private static final String NICKNAME = "nickname";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("회원 가입을 한다.")
    @Test
    void signUp() {
        //given
        final UserCreateRequest request = UserCreateRequest.builder()
                .nickname(NICKNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        //when
        final UserResponse userResponse = userService.signUp(request);

        //then
        assertThat(userResponse.email()).isEqualTo(EMAIL);
        assertThat(userResponse.nickname()).isEqualTo(NICKNAME);
    }
}