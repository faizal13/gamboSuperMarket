package com.gamboSupermarket.application.services;

import java.io.IOException;
import java.util.List;

import com.gamboSupermarket.application.customException.AddCustomerException;
import com.gamboSupermarket.application.dto.User;
import com.gamboSupermarket.application.model.SignUpRequest;

public interface UserService {

	void saveUser(SignUpRequest signUpRequest) throws IOException, AddCustomerException;

	User updateUser(User user);

	void deleteUser(Long userId);

	User findByUsername(String email);

	User findByUsernameOrMobileNumber(String userName);

	List<User> findAllUsers();

	long numberOfUsers();

	User findByUsernameIgnoreCase(String email);

	/*
	 * public Boolean sendVerificationEmail(String email);
	 * 
	 * public Boolean sendPasswordResetEmail(String email);
	 * 
	 * Boolean checkIfUserLoggedInWithGoogle(String email);
	 */
}
