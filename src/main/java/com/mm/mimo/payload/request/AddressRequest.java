package com.mm.mimo.payload.request;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class AddressRequest {
    @NotBlank(message = "Địa chỉ không được để trống")
    private String addressLine;

    @NotBlank(message = "Thành phố không được để trống")
    private String city;

    @NotBlank(message = "Quận/Huyện không được để trống")
    private String district;

}