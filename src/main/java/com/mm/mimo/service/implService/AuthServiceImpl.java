package com.mm.mimo.service.implService;

import com.mm.mimo.entity.Role;
import com.mm.mimo.entity.User;
import com.mm.mimo.enums.RoleEnum;
import com.mm.mimo.payload.request.LoginRequest;
import com.mm.mimo.payload.request.OAuthLoginRequest;
import com.mm.mimo.payload.request.RegisterRequest;
import com.mm.mimo.payload.respone.JwtResponse;
import com.mm.mimo.payload.respone.UserResponse;
import com.mm.mimo.repo.RoleRepository;
import com.mm.mimo.repo.UserRepository;
import com.mm.mimo.security.JwtUtil;
import com.mm.mimo.security.UserPrinciple;
import com.mm.mimo.service.interfaceService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        // Kiểm tra xem username, email, hoặc sđt đã tồn tại chưa
        if (userRepository.existsByEmailOrUsernameOrPhoneNumber(request.getEmail(), request.getUsername(), request.getPhoneNumber())) {
            throw new IllegalArgumentException("Lỗi: Email, Username hoặc Số điện thoại đã được sử dụng!");
        }

        // Tìm vai trò mặc định là ROLE_USER
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy vai trò ROLE_USER."));

        // Tạo người dùng mới
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .fullName(request.getFullName())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .isLocked(false)
                .roles(Set.of(userRole))
                .build();

        User savedUser = userRepository.save(user);

        // Chuyển đổi sang UserResponse để trả về
        return UserResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .fullName(savedUser.getFullName())
                .avatarUrl(savedUser.getAvatarUrl())
                .build();
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        // AuthenticationManager sẽ tự động sử dụng UserDetailsServiceImpl của bạn
        // để tìm người dùng bằng username, email, hoặc số điện thoại.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmailOrPhone(),
                        request.getPassword()
                )
        );

        // Nếu xác thực thành công, đặt vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Lấy thông tin người dùng đã xác thực từ UserPrinciple
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        // Tạo token JWT
        String jwt = jwtUtil.generateToken(Map.of("userId", userPrinciple.getId()), userPrinciple);

        // Lấy danh sách vai trò
        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Trả về JwtResponse đã được cập nhật với đầy đủ thông tin
        return new JwtResponse(
                jwt,
                userPrinciple.getId(),
                roles
        );
    }

    @Override
    public JwtResponse loginWithOAuth(OAuthLoginRequest request) {
        throw new UnsupportedOperationException("Chức năng đăng nhập OAuth2 chưa được triển khai.");
    }
}