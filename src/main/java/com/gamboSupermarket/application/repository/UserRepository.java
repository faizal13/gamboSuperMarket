package com.gamboSupermarket.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamboSupermarket.application.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String email);

	Boolean existsByUsername(String email);

	User findByUsernameOrMobileNumber(String email, String mobileNumber);

	@Query("SELECT u FROM User u where u.username = :userName OR u.mobileNumber = :userName")
	public User findByUserNameOrMobileNumber(@Param("userName") String userName);

	User findByUsernameIgnoreCase(String emailId);
}