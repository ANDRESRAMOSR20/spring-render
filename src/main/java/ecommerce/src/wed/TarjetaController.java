package ecommerce.src.wed;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import ecommerce.src.model.Elementos;
import ecommerce.src.model.Tarjeta;
import ecommerce.src.repository.TarjetaRepository;

@Controller
@RequestMapping("/tarjetas")
public class TarjetaController {





		@Autowired
		private TarjetaRepository tarjetaRepository;
	
	

	  @GetMapping("")
	    public String salonListTemplate(Model model) {
		  
	        model.addAttribute("tarjetas", tarjetaRepository.findAll());
	    
	        return "tarjetas";
	    }
	  
	  

	    @GetMapping("/new")
	    public String elNewTemplate(Model model) {
	        model.addAttribute("tarjeta", new Tarjeta());
	        return "tarjetadd";
	    }
	   

	    @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable String id, Model model) {
	        Optional<Tarjeta> opele = tarjetaRepository.findById(id);

	        if (opele.isPresent()) {
	            Tarjeta tarjeta = opele.get();
	            model.addAttribute("tarjeta", tarjeta);
	            return "tarjetadd";  // Nombre de tu vista de formulario de edición
	        } else {
	            return "redirect:/error";
	        }
	    }


	    @PostMapping("/save")
	    public String EstudianteSaveProcess(@ModelAttribute("tarjeta")  Tarjeta tarjeta, Model model) {
	    	if (tarjeta.getId().isEmpty()) {
	    		tarjeta.setId(null);
	        }
	    	
	    	
	            // Procesar y guardar el salón, incluida la imagen
	    	tarjetaRepository.save(tarjeta);

	            return "redirect:/tarjetas";
	        
	    }
	    
	    
	    
	    
	    
	    
	    
	    
		
	    @GetMapping("/delete/{id}")
	    public String tgDeleteProcess(@PathVariable("id") String id) {
	    	tarjetaRepository.deleteById(id);
	        return "redirect:/tarjetas";
	    }
	    

	    
	    
	}