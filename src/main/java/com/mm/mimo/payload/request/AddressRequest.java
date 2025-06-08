package com.mm.mimo.payload.request;
import lombok.Data;

@Data
public class AddressRequest {
    private String addressLine;
    private String city;
    private String district;
    private String country;
    private String postalCode;
}