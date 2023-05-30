package com.gamboSupermarket.application.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamboSupermarket.application.customException.AddCustomerException;
import com.gamboSupermarket.application.dto.User;
import com.gamboSupermarket.application.mapper.UserDetailMapper;
import com.gamboSupermarket.application.model.SignUpRequest;
import com.gamboSupermarket.application.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailMapper userDetailMapper;

	/*
	 * @Autowired ConfirmationTokenService confirmationTokenService;
	 * 
	 * @Autowired EmailSenderService emailSenderService;
	 * 
	 * @Autowired NotificationService notificationService;
	 */

	/*
	 * @Value("${styleset.user.help.service.url}") private String
	 * userHelpServiceUrl;
	 */

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public User findByUsername(String email) {
		return userRepository.findByUsername(email).orElse(null);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public long numberOfUsers() {
		return userRepository.count();
	}

	@Override
	public User findByUsernameIgnoreCase(String email) {
		return userRepository.findByUsernameIgnoreCase(email);
	}

	@Override
	public void saveUser(SignUpRequest signUpRequest) throws IOException, AddCustomerException {
		logger.debug("user - " + signUpRequest.getEmailId() + " is trying to signup");
		User user = userRepository.findByUsernameOrMobileNumber(signUpRequest.getEmailId(),
				signUpRequest.getMobileNumber());
		logger.debug("found user from database", user);
		if (user != null) {
			logger.debug("user " + signUpRequest.getEmailId() + " already exists");
			throw new AddCustomerException("user Already exist");
		}
		userRepository.save(userDetailMapper.mapUserDetailToDto(signUpRequest));
	}

	@Override
	public User findByUsernameOrMobileNumber(String userName) {
		User user = null;
		user = userRepository.findByUserNameOrMobileNumber(userName);
		return user;
	}

	/*
	 * @Override public Boolean sendVerificationEmail(String email) { User user =
	 * userRepository.findByUsernameIgnoreCase(email); if (user != null) {
	 * notificationService.sendNotificationToUser(user.getId().toString(),user.
	 * getOneSignalPlayerId(),"Welcome To StyleSet"
	 * ,"{\"task\":\"Okay\"}","Greetings!!! Kindly verify your account. Check e-mail."
	 * ); ConfirmationToken confirmationToken = new ConfirmationToken(user);
	 * confirmationTokenService.saevToken(confirmationToken); SimpleMailMessage
	 * mailMessage = new SimpleMailMessage(); mailMessage.setTo(user.getUsername());
	 * mailMessage.setSubject("StyleSet verify your email!");
	 * mailMessage.setFrom("sanketpatwardhan2474@gmail.com");
	 * mailMessage.setText("To verify your email, please click the below link: " +
	 * userHelpServiceUrl+
	 * "/api/auth/help/verify-email?token="+confirmationToken.getConfirmationToken()
	 * ); emailSenderService.sendEmail(mailMessage); return true; } else{ return
	 * false; } }
	 */

	/*
	 * @Override public Boolean sendPasswordResetEmail(String email) { User user =
	 * userRepository.findByUsernameIgnoreCase(email); if (user != null) {
	 * //notificationService.sendNotificationToUser(user.getId().toString(),user.
	 * getOneSignalPlayerId(),"Welcome To StyleSet"
	 * ,"{\"task\":\"Okay\"}","Greetings!!! Kindly verify your account. Check e-mail."
	 * ); ConfirmationToken confirmationToken = new ConfirmationToken(user);
	 * confirmationTokenService.saevToken(confirmationToken); SimpleMailMessage
	 * mailMessage = new SimpleMailMessage(); mailMessage.setTo(user.getUsername());
	 * mailMessage.setSubject("StyleSet Password Reset!");
	 * mailMessage.setFrom("sanketpatwardhan2474@gmail.com"); mailMessage.
	 * setText("To complete the password reset process, please click the below link: "
	 * + userHelpServiceUrl+
	 * "/api/auth/help/confirm-reset?token="+confirmationToken.getConfirmationToken(
	 * )); emailSenderService.sendEmail(mailMessage); return true; } else{ return
	 * false; } }
	 */

	/*
	 * @Override public Boolean checkIfUserLoggedInWithGoogle(String email) { User
	 * user = userRepository.findByUsername(email).orElse(null); if(user!=null &&
	 * user.getIsGoogleLogin()){ return true; } else return false; }
	 */

}
