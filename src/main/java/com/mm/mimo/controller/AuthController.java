package com.mm.mimo.controller;

import com.mm.mimo.payload.request.RegisterRequest;
import com.mm.mimo.payload.respone.ApiResponse;
import com.mm.mimo.payload.respone.UserResponse;
import com.mm.mimo.entity.User;
import com.mm.mimo.service.interfaceService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            User newUser = authService.register(registerRequest);
            UserResponse userResponse = UserResponse.builder()
                    .userId(newUser.getId())
                    .email(newUser.getEmail())
                    .phoneNumber(newUser.getPhoneNumber())
                    .fullName(newUser.getFullName())
                    .avatarUrl(newUser.getAvatarUrl())
                    .build();
            return ResponseEntity.ok(new ApiResponse<>(true, "Đăng ký thành công, vui lòng chờ admin phê duyệt!", userResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // (Thêm API /login ở đây sau khi hoàn thiện logic)
} 