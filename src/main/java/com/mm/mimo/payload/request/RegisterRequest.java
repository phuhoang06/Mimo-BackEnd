package com.mm.mimo.payload.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;         // Có thể null nếu dùng phone
    private String phoneNumber;   // Có thể null nếu dùng email
    private String password;
    private String fullName;      // Optional
}
