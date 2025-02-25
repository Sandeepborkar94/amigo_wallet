package com.infosys.app.service;

import java.util.Optional;

import com.infosys.app.dto.UserDTO;
import com.infosys.app.entity.User;

public interface UserService 
{
	User registerUser(UserDTO userDTO);

	User loginUser(String username, String password);

	Optional<User> findById(Long id);
}
