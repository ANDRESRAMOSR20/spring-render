package ecommerce.shop.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import ecommerce.src.model.User;
import ecommerce.src.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
