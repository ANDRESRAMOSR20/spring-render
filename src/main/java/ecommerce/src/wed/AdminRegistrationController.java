package ecommerce.src.wed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ecommerce.shop.service.UserService;
import ecommerce.shop.service.UserServiceImpl;
import ecommerce.src.web.dto.UserRegistrationDto;


@Controller
@RequestMapping("/registrationAdmin")
public class AdminRegistrationController {
	
	private UserService userService;
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	public AdminRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	
	@ModelAttribute("admin")
    public UserRegistrationDto userRegistrationDtoAdmin() {
        return new UserRegistrationDto();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "registrationAdmin";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("admin") UserRegistrationDto registrationDto) {
		userServiceImpl.saveAdmin(registrationDto);
		return "redirect:/registrationAdmin?success";
	}
}
