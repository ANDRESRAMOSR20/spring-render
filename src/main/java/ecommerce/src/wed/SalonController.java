package ecommerce.src.wed;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import ecommerce.src.model.Elementos;
import ecommerce.src.model.Salon;
import ecommerce.src.model.User;
import ecommerce.src.repository.ElementoRepository;
import ecommerce.src.repository.SalonRepository;
import ecommerce.src.repository.UserRepository;
import ecommerce.shop.service.PdfGeneratorService;




@Controller
@RequestMapping(value = "salones")
public class SalonController {

	@Autowired
    private SalonRepository salonRepository;

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private ElementoRepository elementoRepository;
	
	@Autowired
    private PdfGeneratorService pdfGeneratorService;
	
	  @GetMapping("")
	    public String salonListTemplate(Model model,Principal principal) {
		    String username = principal.getName();
		    model.addAttribute("username", username);

	        model.addAttribute("salones", salonRepository.findAll());
	    
	        return "salones";
	    }

	    @GetMapping("/new")
	    public String salonNewTemplate(Model model) {
	    	List<Elementos> elementosList = elementoRepository.findAll(); 
	    	model.addAttribute("salon", new Salon());
	    	model.addAttribute("elementosList",elementosList);
	       
	        return "Salonadd";
	    }
	   

	    @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable String id, Model model) {
	        Optional<Salon> opsalon = salonRepository.findById(id);
	        List<Elementos> elementosList = elementoRepository.findAll(); 
	        if (opsalon.isPresent()) {
	            Salon salon = opsalon.get();
	            model.addAttribute("salon", salon);
		    	model.addAttribute("elementosList",elementosList);
	            return "SalonEdit";  // Nombre de tu vista de formulario de edición
	        } else {
	            return "redirect:/error";
	        }
	    }
	    
	    
	   
	    
	    
	    
	    


	    @PostMapping("/save")
	    public String EstudianteSaveProcess(@ModelAttribute("salon") Salon salon, @RequestParam("elementos") List<Elementos> elementos,@RequestParam("file") MultipartFile file, Model model) {
	    	salon.setElementos(elementos);
	    	if (salon.getId().isEmpty()) {
	    		salon.setId(null);
	        }
	    	
	    	try {
	            // Verificar si se ha proporcionado una imagen
	            if (!file.isEmpty()) {
	                // Convertir la imagen a bytes y asignarla al salón
	                salon.setImagen(file.getBytes());
	            }

	            // Procesar y guardar el salón, incluida la imagen
	            salonRepository.save(salon);
	            System.out.print(elementos);
	            return "redirect:/salones";
	        } catch (IOException e) {
	            // Manejar la excepción en caso de error al procesar la imagen
	            e.printStackTrace();
	            return "redirect:/error";
	        }
	    }
	    
	   
	   
	    
	    
	

	  
	    
	    @GetMapping("/salones/{id}")
	    public String getSalonById(@PathVariable String id, Model model) {
	        Optional<Salon> salon = salonRepository.findById(id);
	        model.addAttribute("salon", salon);
	        return "salones";
	    }
		
	    @GetMapping("/delete/{id}")
	    public String salonDeleteProcess(@PathVariable("id") String id) {
	    	salonRepository.deleteById(id);
	        return "redirect:/salones";
	    }
	    
	    @GetMapping("/deleten/{nombre}")
	    public String salonDeletenProcess(@PathVariable("nombre") String nombre) {
	    	salonRepository.deleteByNombre(nombre);
	        return "redirect:/salones";
	    }
		

	    @GetMapping("/VerImagen/{id}")
	    public ResponseEntity<byte[]> VerImagen(@PathVariable String id){
	    	
	    	Optional<Salon> opsalon = salonRepository.findById(id);
	    	
	    	if(opsalon.isPresent()) {
	    		Salon salon = opsalon.get();
	    		byte[] imagenBytes = salon.getImagen();
	    		
	    		
	    		HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.IMAGE_JPEG);
	    	
	    	return new ResponseEntity<>(imagenBytes,headers,HttpStatus.OK);
	    	
	    	}else {
	    	return ResponseEntity.notFound().build();
	    	}

	    	
	    	
	    }
	    
	    
	  
	    
	    
		
		
	    @PostMapping("/update")
	    public String actualizarSalon(@ModelAttribute("salon") @RequestParam ("elementos") List<Elementos> elementos,@RequestParam(name = "file", required = false) MultipartFile file, Salon salon, Model model) {
	        salon.setElementos(elementos);
	    	try {
	            Optional<Salon> opsalon = salonRepository.findById(salon.getId());
	            List<Elementos> elementosList = elementoRepository.findAll();

	            if (opsalon.isPresent()) {
	                Salon salonExistente = opsalon.get();
	                model.addAttribute("salon", salon);
	                model.addAttribute("elementosList", elementosList);

	                // Verificar si se ha proporcionado una nueva imagen
	                if (file != null && !file.isEmpty()) {
	                    // Convertir la nueva imagen a bytes y actualizar el salón
	                    salonExistente.setImagen(file.getBytes());
	                }

	                // Actualizar otros campos del salón
	                salonExistente.setNombre(salon.getNombre());
	                salonExistente.setPrecio(salon.getPrecio());
	                salonExistente.setDisponibilidad(salon.getDisponibilidad());
	                salonExistente.setUbicacion(salon.getUbicacion());
	                salonExistente.setCapacidad(salon.getCapacidad());
	                salonExistente.setDireccion(salon.getDireccion());
	                salonExistente.setBarrio(salon.getBarrio());
	                salonExistente.setElementos(salon.getElementos());


	                
	                
	                // Guardar el salón actualizado
	                salonRepository.save(salonExistente);

	                return "redirect:/salones";
	            } else {
	                return "redirect:/salones";
	            }
	        } catch (IOException e) {
	            // Manejar la excepción en caso de error al procesar la imagen
	            e.printStackTrace();
	            return "redirect:/error";
	        }
	    }


		
	    @GetMapping("/pdf")
	    public ResponseEntity<byte[]> generatePdf() {
	        List<Salon> salones = salonRepository.findAll();
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        
	        try {
	            byte[] pdfContent = pdfGeneratorService.generatePdf(salones, authentication);

	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_PDF);
	            headers.setContentDispositionFormData("inline", "salones.pdf");

	            return ResponseEntity.ok().headers(headers).body(pdfContent);
	        } catch (IOException e) {
	            // Manejar excepciones aquí
	            e.printStackTrace();
	            return ResponseEntity.status(500).body(null);
	        }
	    }

	

	    @GetMapping("/versalones")
	    public String verCatalogo(@RequestParam(name = "Ubicacion", required = false) String ciudad,Model model, HttpSession session, Authentication authentication) {
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
	    	  List<Salon> salones;
	    	  List<String> ciudades = salonRepository.findDistinctUbicacion();

	    	  if (ciudad != null && !ciudad.isEmpty()) {
	    	        salones = salonRepository.findAll();  // Obtén todos los salones con todas las propiedades
	    	        // Filtra los salones por la ciudad seleccionada
	    	        salones = salones.stream()
	    	                .filter(salon -> salon.getUbicacion().equalsIgnoreCase(ciudad))
	    	                .collect(Collectors.toList());
	    	    } else {
	    	        salones = salonRepository.findAll();
	    	    }
	          model.addAttribute("ciudades", ciudades);
	         
	    	  User user = userRepository.findByEmail(username);
	    	  
	    	model.addAttribute("username", username);
	        model.addAttribute("salones", salones);
	        return "SalonesUser";
	    }
	    
	    
	    
	    
	    
	    

	    @GetMapping("/userSalones")
	    public String verC(Model model) {	
	        model.addAttribute("salones", salonRepository.findAll());
	        return "SalonesN";
	    }


	    

	    @GetMapping("/salonesvista")
	    public String verCv(Model model) {	
	        model.addAttribute("salones", salonRepository.findAll());
	        return "salonesvista";
	    }
	    
	    
	    
	    
	}