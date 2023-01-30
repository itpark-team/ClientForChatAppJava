package com.example.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterUserRequestDto {
    private String nickname;
    private String login;
    private String password;
}
