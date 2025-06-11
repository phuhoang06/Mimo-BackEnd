package com.mm.mimo.payload.request;
import jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Data
public class UpdateProfileRequest {
    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    private String fullName;

    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
    @Email(message = "Email không hợp lệ")
    private String email;

    private String avatarUrl;

    private Set<String> rolesNames; // Danh sách tên vai trò, ví dụ: ["ROLE_USER", "ROLE_ADMIN"]
}