package com.clientui.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String matchingPassword;
    private String email;
    private boolean enabled;
    private boolean connected;
}
