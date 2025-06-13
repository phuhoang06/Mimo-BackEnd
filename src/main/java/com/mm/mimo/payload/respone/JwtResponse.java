package com.mm.mimo.payload.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UUID id;
    private List<String> roles;

    public JwtResponse(String token, UUID id, List<String> roles) {
        this.token = token;
        this.id = id;
        this.roles = roles;
    }

    public JwtResponse(String token) {
        this.token = token;
    }


}