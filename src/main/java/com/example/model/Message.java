package com.example.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message {
    private long id;
    private User fromUser;
    private User toUser;
    private String text;
    private boolean isOpened;
}
