package com.example.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthUserResponseDto {
    private long id;
    private String nickname;
}
