package com.mm.mimo.payload.request;

import com.mm.mimo.entity.Role;
import com.mm.mimo.validator.PasswordMatch;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Data
@PasswordMatch
public class RegisterRequest {

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Email(message = "Email không hợp lệ")
    private String email;         // Có thể null nếu dùng phone

    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;   // Có thể null nếu dùng email

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 100, message = "Mật khẩu phải từ 6 đến 100 ký tự")
    private String password;

    @NotBlank(message = "Password xác nhận không được để trống")
    private String confirmPassword;

    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    private String fullName;      // Optional

    private Set<Role> roles;
}
