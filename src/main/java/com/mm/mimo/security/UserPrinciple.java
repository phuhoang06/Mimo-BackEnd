package com.mm.mimo.security;

import com.mm.mimo.entity.Role;
import com.mm.mimo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Lớp này đại diện cho người dùng đã xác thực trong hệ thống bảo mật của Spring Security.
 * Cài đặt giao diện UserDetails giúp Spring biết cách xác thực và phân quyền.
 *
 * Phiên bản này đã được cập nhật để bao gồm cả số điện thoại (phone).
 */
public class UserPrinciple implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id; // ID người dùng
    private final String username; // Tên đăng nhập (sử dụng cho việc xác thực)
    private final String password; // Mật khẩu đã được hash
    private final String email;    // Email người dùng
    private final String phoneNumber;    // Số điện thoại người dùng
    private final Collection<? extends GrantedAuthority> authorities; // Danh sách quyền (ROLE_XXX)
    private final boolean isLocked; // Trạng thái bị khóa hay không

    /**
     * Constructor đầy đủ các thuộc tính.
     *
     * @param id          ID của người dùng.
     * @param username    Tên đăng nhập.
     * @param password    Mật khẩu đã hash.
     * @param email       Email.
     * @param phoneNumber       Số điện thoại.
     * @param authorities Các quyền của người dùng.
     * @param isLocked    Trạng thái tài khoản có bị khóa không.
     */
    public UserPrinciple(UUID id, String username, String password, String email, String phoneNumber,
                         Collection<? extends GrantedAuthority> authorities,
                         boolean isLocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
        this.isLocked = isLocked;
    }

    /**
     * Factory method để chuyển từ User entity sang UserPrinciple dùng cho Spring Security.
     *
     * @param user Đối tượng User lấy từ database.
     * @return Đối tượng UserPrinciple phù hợp với Spring Security.
     */
    public static UserPrinciple build(User user) {
        Set<Role> roles = user.getRoles(); // Lấy các vai trò của người dùng

        // Chuyển đổi danh sách role thành danh sách authority (ROLE_XXX)
        List<GrantedAuthority> authorities = roles.stream()
                .filter(Objects::nonNull)
                .map(Role::getName) // Lấy enum RoleEnum
                .filter(Objects::nonNull)
                .map(Enum::name) // Chuyển thành chuỗi (ROLE_USER, ROLE_ADMIN,...)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Tạo UserPrinciple từ các thông tin trong entity User
        return new UserPrinciple(
                user.getId(),
                user.getUsername(),      // Tên đăng nhập
                user.getPasswordHash(),  // Mật khẩu đã hash
                user.getEmail(),         // Email
                user.getPhoneNumber(),         // Số điện thoại (giả định User entity có phương thức getPhone())
                authorities,
                Boolean.TRUE.equals(user.getIsLocked())
        );
    }

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // --- UserDetails interface methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Mặc định không xử lý ngày hết hạn tài khoản
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked; // Tài khoản không bị khóa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Mặc định không xử lý hết hạn mật khẩu
    }

    @Override
    public boolean isEnabled() {
        return true; // Mặc định tài khoản luôn được kích hoạt
    }

    // --- equals() and hashCode() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrinciple that = (UserPrinciple) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
