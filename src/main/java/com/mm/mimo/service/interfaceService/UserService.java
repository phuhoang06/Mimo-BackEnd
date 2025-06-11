package com.mm.mimo.service.interfaceService;

import com.mm.mimo.entity.Address;
import com.mm.mimo.payload.request.AddressRequest;
import com.mm.mimo.payload.request.UpdateProfileRequest;
import com.mm.mimo.payload.respone.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;


/**
 * Interface cho các dịch vụ liên quan đến quản lý thông tin của người dùng đã xác thực.
 */
public interface UserService {
    /**
     * Tải thông tin người dùng dựa trên username (hoặc email/SĐT) cho Spring Security.
     * Phương thức này là một phần của interface UserDetailsService.
     *
     * @param username định danh của người dùng.
     * @return UserDetails chứa thông tin chi tiết của người dùng.
     * @throws UsernameNotFoundException nếu không tìm thấy người dùng.
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Lấy thông tin hồ sơ của người dùng dựa vào ID.
     *
     * @param userId ID của người dùng (thường lấy từ token JWT).
     * @return UserResponse chứa thông tin hồ sơ người dùng.
     */
    UserResponse getUserProfile(UUID userId);

    /**
     * Cập nhật thông tin hồ sơ cho người dùng hiện tại.
     *
     * @param userId ID của người dùng cần cập nhật.
     * @param request Dữ liệu mới để cập nhật.
     * @return UserResponse chứa thông tin hồ sơ đã được cập nhật.
     */
    UserResponse updateProfile(UUID userId, UpdateProfileRequest request);

    /**
     * Lấy danh sách tất cả địa chỉ của một người dùng.
     *
     * @param userId ID của người dùng.
     * @return Danh sách các địa chỉ.
     */
    List<Address> getAddressesByUserId(UUID userId);

    /**
     * Thêm hoặc cập nhật một địa chỉ cho người dùng.
     * Nếu ID địa chỉ có trong request, nó sẽ được cập nhật. Nếu không, một địa chỉ mới sẽ được tạo.
     *
     * @param userId ID của người dùng sở hữu địa chỉ.
     * @param request Dữ liệu địa chỉ mới hoặc cần cập nhật.
     * @return Địa chỉ đã được lưu.
     */
    Address addOrUpdateAddress(UUID userId, AddressRequest request);

    /**
     * Xóa một địa chỉ của người dùng.
     *
     * @param userId ID của người dùng.
     * @param addressId ID của địa chỉ cần xóa.
     */
    void deleteAddress(UUID userId, UUID addressId);
}