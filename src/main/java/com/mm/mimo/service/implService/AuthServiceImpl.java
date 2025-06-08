package com.mm.mimo.service.implService;

import com.mm.mimo.entity.User;
import com.mm.mimo.payload.request.LoginRequest;
import com.mm.mimo.payload.request.OAuthLoginRequest;
import com.mm.mimo.payload.request.RegisterRequest;
import com.mm.mimo.repo.OauthAccountRepository;
import com.mm.mimo.repo.UserRepository;
import com.mm.mimo.service.interfaceService.AuthSevice;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServiceImpl implements AuthSevice {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OauthAccountRepository oauthAccountRepository;

    @Override
    public User register(RegisterRequest request) {
        return null;
    }

    @Override
    public User login(LoginRequest request) {
        return null;
    }

    @Override
    public User loginWithOAuth(OAuthLoginRequest request) {
        return null;
    }
}
