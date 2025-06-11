package com.mm.mimo.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String usernameOrEmailOrPhone; // Tên đăng nhập, email hoặc số điện thoại
    private String password;
}
