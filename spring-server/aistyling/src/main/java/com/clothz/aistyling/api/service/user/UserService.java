package com.clothz.aistyling.api.service.user;

import com.clothz.aistyling.api.controller.dto.request.UserCreateRequest;
import com.clothz.aistyling.api.service.user.response.UserResponse;
import com.clothz.aistyling.domain.user.User;
import com.clothz.aistyling.domain.user.UserRepository;
import com.clothz.aistyling.domain.user.constant.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponse signUp(final UserCreateRequest request) {
        final String encodePassword = passwordEncoder.encode(request.password());
        final User user = userRepository.save(createUserEntity(request, encodePassword));
        return UserResponse.from(user);
    }

    private User createUserEntity(UserCreateRequest request, String encodePassword) {
        return User.builder()
                .email(request.email())
                .nickname(request.nickname())
                .password(encodePassword)
                .userRole(UserRole.USER)
                .build();
    }
}
