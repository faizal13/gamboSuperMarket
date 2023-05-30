package com.gamboSupermarket.application.mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gamboSupermarket.application.dto.Categories;
import com.gamboSupermarket.application.dto.Role;
import com.gamboSupermarket.application.dto.User;
import com.gamboSupermarket.application.model.ERole;
import com.gamboSupermarket.application.model.SignUpRequest;
import com.gamboSupermarket.application.repository.RoleRepository;

@Component
public class UserDetailMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailMapper.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public User mapUserDetailToDto(SignUpRequest signUpRequest) throws IOException {

		User user = new User();
		if (signUpRequest != null) {
			user.setMobileNumber(signUpRequest.getMobileNumber());
			user.setName(signUpRequest.getName());
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			user.setUsername(signUpRequest.getEmailId());
			user.setStatus(true);
			user.setRoles(mapRoles(signUpRequest.getRole()));
		}
		return user;
	}

	private Set<Role> mapRoles(Set<String> strRoles) {

		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLES_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ROLES_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "ROLE_MERCHANT":
					Role modRole = roleRepository.findByName(ERole.ROLES_MERCHANT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLES_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		return roles;
	}
}
