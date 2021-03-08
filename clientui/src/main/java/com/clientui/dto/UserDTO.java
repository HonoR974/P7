package com.clientui.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private String matchingPassword;
    private String email;
}
