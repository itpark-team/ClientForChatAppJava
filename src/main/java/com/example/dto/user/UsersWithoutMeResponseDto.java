package com.example.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsersWithoutMeResponseDto {
    private long id;
    private String nickname;
    private boolean isOnline;
}
