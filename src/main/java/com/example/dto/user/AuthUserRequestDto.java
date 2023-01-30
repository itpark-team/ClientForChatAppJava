package com.example.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthUserRequestDto {
    private String login;
    private String password;
}
