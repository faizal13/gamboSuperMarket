package com.gamboSupermarket.application.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamboSupermarket.application.auth.JwtTokenProvider;
import com.gamboSupermarket.application.customException.AddCustomerException;
import com.gamboSupermarket.application.dto.User;
import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.EmptyJsonResponse;
import com.gamboSupermarket.application.model.ForgotPasswordRequest;
import com.gamboSupermarket.application.model.JwtResponse;
import com.gamboSupermarket.application.model.LoginRequest;
import com.gamboSupermarket.application.model.SignUpRequest;
import com.gamboSupermarket.application.repository.OrderRepository;
import com.gamboSupermarket.application.repository.RoleRepository;
import com.gamboSupermarket.application.repository.UserRepository;
import com.gamboSupermarket.application.services.UserDetailsImpl;
import com.gamboSupermarket.application.services.UserService;
import com.gamboSupermarket.application.utils.ResponseUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtTokenProvider jwtUtils;

	@Autowired
	UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@CrossOrigin()
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		logger.debug("user - " + loginRequest.getUsername() + " is trying to login");

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userService.findByUsernameOrMobileNumber(loginRequest.getUsername());
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		String refNumber = orderRepository.getRefNumber(user.getId(), "ORD6");

		return ResponseEntity
				.ok(new JwtResponse(jwt, user.getId(), user.getUsername(), roles, user.getName(), refNumber));
	}

	@PostMapping(value = "/signup")
	public ResponseEntity<CustomResponse<Object>> registerUser(@Valid @RequestBody SignUpRequest signUpRequest,
			HttpServletRequest request) throws IOException, AddCustomerException {
		userService.saveUser(signUpRequest);
		return new ResponseEntity<>(
				ResponseUtils.getCustomResponse(200, new EmptyJsonResponse(), "sign up successful", request),
				HttpStatus.OK);
	}

	@PostMapping(value = "/forgotPassword")
	public ResponseEntity<CustomResponse<Object>> forgotPassword(
			@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request)
			throws IOException, AddCustomerException {
		User user = userService.findByUsernameOrMobileNumber(forgotPasswordRequest.getMobileNumber());
		if (user == null) {
			throw new UsernameNotFoundException("userId does not exist");
		}
		user.setPassword(passwordEncoder.encode(forgotPasswordRequest.getPassword()));
		userService.updateUser(user);
		return new ResponseEntity<>(
				ResponseUtils.getCustomResponse(200, new EmptyJsonResponse(), "password updated successfully", request),
				HttpStatus.OK);
	}

}
