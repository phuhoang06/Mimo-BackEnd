package com.mm.mimo.payload.request;
import lombok.Data;

@Data
public class OAuthLoginRequest {
    private String provider;      // "GOOGLE" hoặc "FACEBOOK"
    private String providerId;    // ID từ Google/Facebook
    private String email;         // Email từ provider (nếu có)
    private String avatarUrl;     // Ảnh đại diện từ provider (nếu có)
}