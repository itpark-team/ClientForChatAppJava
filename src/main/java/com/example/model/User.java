package com.example.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private long id;
    private String name;
    private String login;
    private String password;
    private boolean isOnline;
}
