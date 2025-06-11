package com.mm.mimo.service.interfaceService;

import com.mm.mimo.payload.request.LoginRequest;
import com.mm.mimo.payload.request.OAuthLoginRequest;
import com.mm.mimo.payload.request.RegisterRequest;
import com.mm.mimo.payload.respone.JwtResponse;
import com.mm.mimo.payload.respone.UserResponse;

/**
 * Interface cho các dịch vụ liên quan đến xác thực người dùng như đăng ký, đăng nhập.
 */
public interface AuthService {

    /**
     * Xử lý yêu cầu đăng ký người dùng mới.
     *
     * @param request Chứa thông tin đăng ký từ người dùng.
     * @return UserResponse Chứa thông tin cơ bản của người dùng vừa được tạo.
     * @throws RuntimeException nếu email hoặc số điện thoại đã tồn tại.
     */
    UserResponse register(RegisterRequest request);

    /**
     * Xử lý yêu cầu đăng nhập bằng username/email/phone và mật khẩu.
     *
     * @param request Chứa thông tin đăng nhập.
     * @return JwtResponse Chứa token JWT và thông tin người dùng sau khi đăng nhập thành công.
     * @throws org.springframework.security.core.AuthenticationException nếu thông tin không hợp lệ.
     */
    JwtResponse login(LoginRequest request);

    /**
     * Xử lý yêu cầu đăng nhập thông qua nhà cung cấp OAuth2 (Google, Facebook).
     *
     * @param request Chứa thông tin từ nhà cung cấp OAuth2.
     * @return JwtResponse Chứa token JWT và thông tin người dùng.
     */
    JwtResponse loginWithOAuth(OAuthLoginRequest request);
}