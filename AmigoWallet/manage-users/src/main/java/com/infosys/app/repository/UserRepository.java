package com.infosys.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> 
{
	Optional<User> findByUsername(String username);
}
