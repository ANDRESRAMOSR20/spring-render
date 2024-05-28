package ecommerce.src.wed;

import java.security.Principal;
import java.util.Arrays;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ecommerce.src.model.Inquietud;
import ecommerce.src.model.Role;
import ecommerce.src.model.User;
import ecommerce.src.repository.InquietudRepository;
import ecommerce.src.repository.UserRepository;

@Controller
public class MainController {
	
	
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private InquietudRepository inquietudRepository;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/")
	public String home() {
		return "indexx";
	}
	

	

    
    @GetMapping("/gestionAdmin") 
    public String gestionAdmin(Model model, Principal principal) {
    	
    	 // Obtiene el nombre de usuario del Principal
	    String username = principal.getName();
	    model.addAttribute("username", username);
	    // Busca al usuario en tu servicio de usuario (cámbialo según tu implementación)
	    User usuario = userRepository.findByEmail(username);

	    if (usuario != null) {
	        // Obtiene el contador de sesiones del usuario
	        int contadorSesiones = usuario.getContadorSesiones();

	        // Pasa el contador al modelo
	        model.addAttribute("contadorSesiones", contadorSesiones);
	    }
    	
    	
    	
        return "gestionAdmin"; 
    }
    
    
    
    @GetMapping("/login/oauth2/success")
    public String loginSuccess(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = getEmailFromAuthentication(authentication);

        // Busca al usuario en tu servicio de usuario o repositorio (cámbialo según tu implementación)
        User existingUser = userRepository.findByEmail(username);

        model.addAttribute("username", username);
        if (existingUser == null) {
            // El correo no existe, crea un nuevo usuario y guárdalo en la base de datos
            User newUser = new User(username, Arrays.asList(new Role("USER")));
            userRepository.save(newUser);
        } else {
            // Usuario existente, actualiza el contador de sesiones
            existingUser.setContadorSesiones(existingUser.getContadorSesiones() + 1);
            userRepository.save(existingUser);
        }
        
    if(existingUser!=null) {
	        // Obtiene el contador de sesiones del usuario
	        int contadorSesiones = existingUser.getContadorSesiones();

	        // Pasa el contador al modelo
	        model.addAttribute("contadorSesiones", contadorSesiones);
    }

        return "indexs";
    }
    
    
    
    

    private String getEmailFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            return oAuth2User.getAttribute("email");
        }
        return null; // o manejar según tus necesidades si el principal no es un OAuth2User
    }

    
    @GetMapping({"/index"})
    public String handleIndex(Model model, HttpSession session, Authentication authentication) {
        String username = "N/A";

        if (authentication != null) {
            if (authentication instanceof OAuth2AuthenticationToken) {
                // Obtener el nombre de usuario de OAuth2
                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                username = oauthToken.getPrincipal().getAttribute("email"); // Reemplaza "email" con el atributo correcto
            } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                // Obtener el nombre de usuario de Spring Security
                UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
                username = auth.getName();
            }
        }

        User user = userRepository.findByEmail(username);

        if (user == null) {
            // El usuario no existe, crea un nuevo usuario y guárdalo en la base de datos
            User newUser = new User(username, Arrays.asList(new Role("USER")));
            userRepository.save(newUser);
        }

        // Verifica si ya se ha incrementado el contador de sesiones en esta sesión
        Boolean contadorIncrementado = (Boolean) session.getAttribute("contadorIncrementado");

        if (user != null && (contadorIncrementado == null || !contadorIncrementado)) {
            // Incrementa el contador de sesiones del usuario
            user.setContadorSesiones(user.getContadorSesiones() + 1);
            userRepository.save(user);

            // Marca la bandera en la sesión para evitar incrementar nuevamente
            session.setAttribute("contadorIncrementado", true);
        }

        // Pasa el nombre de usuario y el contador de sesiones al modelo
        
        model.addAttribute("username", username);
        model.addAttribute("contadorSesiones", user != null ? user.getContadorSesiones() : 1);

        return "index"; // Puedes redirigir a la vista común
    }
    
    
    @GetMapping("/blogsN")
    public String blos(){
    	return  "blogsN";
    }
    
    @GetMapping("/BlogUser")
    public String BlogUser(){
    	return  "BlogUser";
    }
    

    @GetMapping("/acercade")
    public String acercade(){
    	return  "acerca";
    }
    
    @GetMapping("/acercadea")
    public String acercadea(){
    	return  "acercadea";
    }
    
    @GetMapping("/acercadeu")
    public String acercadeu(Model model, Authentication authentication){
    	   String username = "N/A";

           if (authentication != null) {
               if (authentication instanceof OAuth2AuthenticationToken) {
                   // Obtener el nombre de usuario de OAuth2
                   OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                   username = oauthToken.getPrincipal().getAttribute("email"); // Reemplaza "email" con el atributo correcto
               } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                   // Obtener el nombre de usuario de Spring Security
                   UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
                   username = auth.getName();
               }
           }
        model.addAttribute("username", username); 
    	return  "acercadeu";
    }
    
    
    

    @GetMapping("/contactanos")
    public String contactenos(Model model, Authentication authentication){
    	   String username = "N/A";

           if (authentication != null) {
               if (authentication instanceof OAuth2AuthenticationToken) {
                   // Obtener el nombre de usuario de OAuth2
                   OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                   username = oauthToken.getPrincipal().getAttribute("email"); // Reemplaza "email" con el atributo correcto
               } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                   // Obtener el nombre de usuario de Spring Security
                   UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
                   username = auth.getName();
               }
           }
        model.addAttribute("username", username); 
    	model.addAttribute("inquietud", new Inquietud()); 
    	return  "contactenos";
    }
    
    

    @GetMapping("/contactanosa")
    public String contactenosu(){
    	return  "contactenosa";
    }
    
    
    

    @GetMapping("/contactanosu")
    public String contactenosu(Model model, Authentication authentication){
    	   String username = "N/A";

           if (authentication != null) {
               if (authentication instanceof OAuth2AuthenticationToken) {
                   // Obtener el nombre de usuario de OAuth2
                   OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                   username = oauthToken.getPrincipal().getAttribute("email"); // Reemplaza "email" con el atributo correcto
               } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                   // Obtener el nombre de usuario de Spring Security
                   UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
                   username = auth.getName();
               }
           }
        model.addAttribute("username", username); 
    	model.addAttribute("inquietud", new Inquietud()); 
    	return  "contactenosu";
    }
    
    
    @PostMapping("/save")
    public String inquietudSaveProcess(@ModelAttribute("inquietud")  Inquietud inquietud, Model model) {
    	if (inquietud.getId().isEmpty()) {
    		inquietud.setId(null);
        }
    	
    	inquietudRepository.save(inquietud);

            return "redirect:/contactanosu";
        
    }
    
   
    

    
    
}