package com.infosys.app.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.infosys.app.dto.UserDTO;
import com.infosys.app.entity.User;
import com.infosys.app.entity.Wallet;
import com.infosys.app.repository.UserRepository;
import com.infosys.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User registerUser(UserDTO userDTO) {
		User user = User.builder().username(userDTO.getUsername()).password((userDTO.getPassword()))
				.email(userDTO.getEmail()).build();
		
		
		return userRepository.save(user);
	}

	@Override
	public User loginUser(String username, String password) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			
			return user;
		}
		throw new RuntimeException("Invalid credentials");
	}

	@Override
	public Optional<User> findById(Long id) 
	{
		return userRepository.findById(id);
	}
}
