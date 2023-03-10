package com.example.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private long id;
    private String nickname;
}
