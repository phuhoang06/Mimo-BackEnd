package com.mm.mimo.service.interfaceService;

import com.mm.mimo.entity.User;
import com.mm.mimo.payload.request.LoginRequest;
import com.mm.mimo.payload.request.OAuthLoginRequest;
import com.mm.mimo.payload.request.RegisterRequest;

public interface AuthSevice {
    User register(RegisterRequest request);
    User login(LoginRequest request);
    User loginWithOAuth(OAuthLoginRequest request);
}
