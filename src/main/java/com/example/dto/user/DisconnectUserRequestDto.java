package com.example.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DisconnectUserRequestDto {
    private long id;
}
