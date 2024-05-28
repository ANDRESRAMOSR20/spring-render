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

import ecommerce.src.repository.ElementoRepository;





@Controller
@RequestMapping(value = "elementos")
public class ElementoController {



		@Autowired
		private ElementoRepository elementoRepository;
	
	

	  @GetMapping("")
	    public String salonListTemplate(Model model) {
		  
	        model.addAttribute("elementos", elementoRepository.findAll());
	    
	        return "Elementos";
	    }
	  
	  

	    @GetMapping("/new")
	    public String elNewTemplate(Model model) {
	        model.addAttribute("elemento", new Elementos());
	        return "Elementadd";
	    }
	   

	    @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable String id, Model model) {
	        Optional<Elementos> opele = elementoRepository.findById(id);

	        if (opele.isPresent()) {
	            Elementos elemento = opele.get();
	            model.addAttribute("elemento", elemento);
	            return "Elementadd";  // Nombre de tu vista de formulario de edición
	        } else {
	            return "redirect:/error";
	        }
	    }


	    @PostMapping("/save")
	    public String EstudianteSaveProcess(@ModelAttribute("elemento")  Elementos elemento, Model model) {
	    	if (elemento.getId().isEmpty()) {
	    		elemento.setId(null);
	        }
	    	
	    	
	            // Procesar y guardar el salón, incluida la imagen
	    	elementoRepository.save(elemento);

	            return "redirect:/elementos";
	        
	    }
	    

		
	    @GetMapping("/delete/{id}")
	    public String elementoDeleteProcess(@PathVariable("id") String id) {
	    	elementoRepository.deleteById(id);
	        return "redirect:/elementos";
	    }
	    

	    
	    
	}