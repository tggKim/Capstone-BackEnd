package com.clothz.aistyling.api.service.user;

import com.clothz.aistyling.api.controller.dto.request.UserCreateRequest;
import com.clothz.aistyling.api.service.user.response.UserImagesResponse;
import com.clothz.aistyling.api.service.user.response.UserInfoResponse;
import com.clothz.aistyling.api.service.user.response.UserSingUpResponse;
import com.clothz.aistyling.domain.user.User;
import com.clothz.aistyling.domain.user.UserRepository;
import com.clothz.aistyling.domain.user.constant.UserRole;
import com.clothz.aistyling.global.s3.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserSingUpResponse signUp(final UserCreateRequest request) {
        sameCheckEmail(request.email());
        final String encodePassword = passwordEncoder.encode(request.password());
        final User user = userRepository.save(createUserEntity(request, encodePassword));
        return UserSingUpResponse.from(user);
    }

    private User createUserEntity(final UserCreateRequest request, final String encodePassword) {
        return User.builder()
                .email(request.email())
                .nickname(request.nickname())
                .password(encodePassword)
                .userRole(UserRole.USER)
                .build();
    }

    public void sameCheckEmail(final String email) {
        userRepository.findByEmail(email).ifPresent(n -> {
            throw new DuplicateKeyException("Email already exists");
        });
    }

    public UserImagesResponse uploadUserImg(final List<String> imgUrls, final Long id) throws IOException {
        if(imgUrls.isEmpty())
            throw new IllegalArgumentException("Image is empty");
        final User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        final String saveImages = serializeImageUrls(imgUrls);
        user.saveImages(saveImages);
        userRepository.save(user);
        return UserImagesResponse.from(imgUrls);
    }

    private String serializeImageUrls(final List<String> imgUrls) throws JsonProcessingException {
        return objectMapper.writeValueAsString(imgUrls);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(final Long id) throws JsonProcessingException {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        final List<String> imgUrls = deserializeImageUrls(user);
        return UserInfoResponse.of(user, imgUrls);
    }

    private List<String> deserializeImageUrls(final User user) throws JsonProcessingException {
        if(null == user.getUserImages())
            return List.of();
        return objectMapper.readValue(user.getUserImages(), new TypeReference<List<String>>() {});
    }
}
