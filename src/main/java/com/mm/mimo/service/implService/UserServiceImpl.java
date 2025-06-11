package com.mm.mimo.service.implService;

import com.mm.mimo.entity.Address;
import com.mm.mimo.entity.User;
import com.mm.mimo.payload.request.AddressRequest;
import com.mm.mimo.payload.request.UpdateProfileRequest;
import com.mm.mimo.payload.respone.UserResponse;
import com.mm.mimo.repo.AddressRepository;
import com.mm.mimo.repo.UserRepository;
import com.mm.mimo.security.UserPrinciple;
import com.mm.mimo.service.interfaceService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     * Phương thức này của UserDetailsService được Spring Security sử dụng để tải thông tin người dùng.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmailOrPhoneNumber(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với: " + usernameOrEmailOrPhone));

        return UserPrinciple.build(user);
    }

    @Override
    public UserResponse getUserProfile(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với ID: " + userId));

        return UserResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                // .status(...) // Cần thêm trường status vào UserResponse nếu muốn hiển thị
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với ID: " + userId));

        // Cập nhật các trường nếu có trong request
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhoneNumber() != null) {
            // (Optional) Thêm logic kiểm tra SĐT mới có bị trùng không
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }
        // Logic cập nhật email cần cẩn thận hơn vì liên quan đến xác thực
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        User updatedUser = userRepository.save(user);
        return getUserProfile(updatedUser.getId()); // Gọi lại để đảm bảo dữ liệu đồng nhất
    }

    @Override
    public List<Address> getAddressesByUserId(UUID userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Address addOrUpdateAddress(UUID userId, AddressRequest request) {
        // Logic này đang giả định là "Thêm mới".
        // Để "Cập nhật", cần truyền thêm addressId.
        Address address = Address.builder()
                .userId(userId)
                .addressLine(request.getAddressLine())
                .city(request.getCity())
                .district(request.getDistrict())
                // .country(...) // Thêm các trường còn lại nếu cần
                // .postalCode(...)
                .build();

        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteAddress(UUID userId, UUID addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ với ID: " + addressId));

        // Rất quan trọng: Đảm bảo người dùng chỉ có thể xóa địa chỉ của chính mình
        if (!address.getUserId().equals(userId)) {
            throw new SecurityException("Bạn không có quyền xóa địa chỉ này.");
        }

        addressRepository.delete(address);
    }
}