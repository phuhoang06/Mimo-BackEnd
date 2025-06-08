package com.mm.mimo.payload.request;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String fullName;
    private String phoneNumber;
    private String avatarUrl;
}