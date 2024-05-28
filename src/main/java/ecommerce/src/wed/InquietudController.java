package ecommerce.src.wed;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ecommerce.src.model.Elementos;
import ecommerce.src.model.Inquietud;
import ecommerce.src.repository.InquietudRepository;

@Controller
@RequestMapping(value = "inquietudes")
public class InquietudController {
	
	@Autowired
	private InquietudRepository inquietudRepository;
	
	 @GetMapping("")
	    public String reListTemplate(Model model) {
		
	        model.addAttribute("inquietudes", inquietudRepository.findAll());
	    
	        return "inquietudes";
	    }
	 
	 

	   
	    
	    
/*
	    @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable String id, Model model) {
	        Optional<Inquietud> opele = inquietudRepository.findById(id);

	        if (opele.isPresent()) {
	            Inquietud inquietud = opele.get();
	            model.addAttribute("inquietud", inquietud);
	            return "Elementadd";  // Nombre de tu vista de formulario de edición
	        } else {
	            return "redirect:/error";
	        }
	    }
	    */

	    @GetMapping("/delete/{id}")
	    public String inqueitudDeleteProcess(@PathVariable("id") String id) {
	    	inquietudRepository.deleteById(id);
	        return "redirect:/inquietudes";
	    }
	    
	    
	    
	    
	  

}
