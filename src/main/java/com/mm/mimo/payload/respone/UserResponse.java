package com.mm.mimo.payload.respone;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID userId;
    private String email;
    private String phoneNumber;
    private String fullName;
    private String avatarUrl;
    private boolean isLocked;
}
