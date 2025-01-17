package com.service.small.square.application.dto.users;

import lombok.Data;

@Data
public class UsersResponse {
    private String id;
    private String fistName;
    private String lastName;
    private String password;
    private String documentId;
    private String phone;
    private String email;
}
