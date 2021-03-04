package com.bibliotheque.service;

import com.bibliotheque.dto.UserDTO;
import com.bibliotheque.model.User;

public interface UserService {

    User save(UserDTO userDTO);
}
