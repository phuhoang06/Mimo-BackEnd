package com.mm.mimo.payload.request;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class OAuthLoginRequest {
    @NotBlank(message = "Provider không được để trống")
    private String provider;      // "GOOGLE" hoặc "FACEBOOK"

    @NotBlank(message = "ProviderId không được để trống")
    private String providerId;    // ID từ Google/Facebook

    @Email(message = "Email không hợp lệ")
    private String email;         // Email từ provider (nếu có)

    private String avatarUrl;     // Ảnh đại diện từ provider (nếu có)
}