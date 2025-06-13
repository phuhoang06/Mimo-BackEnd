package com.mm.mimo.controller;

import com.mm.mimo.payload.request.LoginRequest;
import com.mm.mimo.payload.request.RegisterRequest;
import com.mm.mimo.payload.respone.ApiResponse;
import com.mm.mimo.payload.respone.JwtResponse;
import com.mm.mimo.payload.respone.UserResponse;
import com.mm.mimo.service.interfaceService.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * Controller chịu trách nhiệm cho các endpoint xác thực như đăng ký và đăng nhập.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint để đăng ký người dùng mới.
     *
     * @param registerRequest Dữ liệu đăng ký từ client, đã được validate.
     * @return ResponseEntity chứa ApiResponse với thông tin người dùng nếu thành công,
     * hoặc thông báo lỗi nếu thất bại.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            // Gọi service để xử lý logic đăng ký.
            // Phương thức register đã trả về một UserResponse.
            UserResponse userResponse = authService.register(registerRequest);

            // Tạo phản hồi thành công.
            ApiResponse response = new ApiResponse(true, "Đăng ký người dùng thành công!", userResponse);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Bắt lỗi cụ thể khi email, username, hoặc SĐT đã được sử dụng.
            ApiResponse response = new ApiResponse(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Endpoint để xác thực người dùng và cấp token JWT.
     *
     * @param loginRequest Dữ liệu đăng nhập từ client.
     * @return ResponseEntity chứa ApiResponse với JWT và thông tin người dùng nếu thành công,
     * hoặc lỗi 401 Unauthorized nếu thông tin không chính xác.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Gọi service để xử lý logic đăng nhập.
            // Phương thức login trả về JwtResponse khi xác thực thành công.
            JwtResponse jwtResponse = authService.login(loginRequest);

            // Tạo phản hồi thành công.
            ApiResponse response = new ApiResponse(true, "Đăng nhập thành công!", jwtResponse);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // Spring Security ném lỗi này khi quá trình xác thực thất bại (sai username/password).
            ApiResponse response = new ApiResponse(false, "Sai thông tin đăng nhập hoặc mật khẩu.", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}