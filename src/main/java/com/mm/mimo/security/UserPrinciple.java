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
 */
public class UserPrinciple implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id; // ID người dùng
    private final String username; // Tên đăng nhập
    private final String password; // Mật khẩu đã được hash
    private final Collection<? extends GrantedAuthority> authorities; // Danh sách quyền (ROLE_XXX)
    private final boolean isLocked; // Trạng thái bị khóa hay không

    // Constructor đầy đủ các thuộc tính
    public UserPrinciple(UUID id, String username, String password,
                         Collection<? extends GrantedAuthority> authorities,
                         boolean isLocked) {
        this.id = id;
        this.username = username;
        this.password = password;
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
                user.getUsername(),
                user.getPasswordHash(),
                authorities,
                Boolean.TRUE.equals(user.getIsLocked())
        );
    }

    // Getter cho ID
    public UUID getId() {
        return id;
    }

    // Getter cho danh sách quyền (dùng trong Spring Security để phân quyền)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Getter cho mật khẩu
    @Override
    public String getPassword() {
        return password;
    }

    // Getter cho username
    @Override
    public String getUsername() {
        return username;
    }

    // Mặc định không xử lý ngày hết hạn tài khoản => luôn trả về true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Nếu tài khoản KHÔNG bị khóa thì trả về true
    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    // Mặc định không xử lý hết hạn mật khẩu => luôn trả về true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Mặc định tài khoản luôn được kích hoạt => true
    @Override
    public boolean isEnabled() {
        return true;
    }

    // So sánh 2 UserPrinciple theo ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPrinciple)) return false;
        UserPrinciple that = (UserPrinciple) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
