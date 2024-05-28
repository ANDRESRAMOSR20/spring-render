package ecommerce.src.wed;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import ecommerce.src.Exception.NotFoundException;
import ecommerce.src.model.Elementos;
import ecommerce.src.model.Reserva;
import ecommerce.src.model.Salon;
import ecommerce.src.model.Tarjeta;
import ecommerce.src.model.User;
import ecommerce.src.repository.ElementoRepository;
import ecommerce.src.repository.ReservaRepository;
import ecommerce.src.repository.SalonRepository;
import ecommerce.src.repository.TarjetaRepository;
import ecommerce.src.repository.UserRepository;
import ecommerce.shop.service.PdfGeneratorService;
import ecommerce.shop.service.TarjetaService;


@Controller
@RequestMapping(value = "reservas")
public class ReservaController {


			@Autowired
			private ElementoRepository elementoRepository;
		
			@Autowired
			private ReservaRepository reservaRepository;
			
			@Autowired
			private SalonRepository salonRepository;
			
			@Autowired
			private UserRepository userRepository;
			
			@Autowired
			private TarjetaRepository tarjetaRepository;
			
			@Autowired
			private TarjetaService tarjetaService;
			


			@Autowired
		    private PdfGeneratorService pdfGeneratorService;
		

		  @GetMapping("")
		    public String reListTemplate(Model model,Authentication authentication) {
			  String username = "N/A";

		      if (authentication != null) {
		          if (authentication instanceof OAuth2AuthenticationToken) {
		              OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		              username = oauthToken.getPrincipal().getAttribute("email");
		          } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
		              UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		              username = auth.getName();
		          }
		      }
			    model.addAttribute("username", username);
		        model.addAttribute("reservas", reservaRepository.findAll());
		    
		        return "Reservas";
		    }
		  
		  
		  @GetMapping("/new")
		  public String reservasNewTemplate(@RequestParam("salonid") String salonId, @RequestParam String salonNombre, Model model,
				  Authentication authentication,RedirectAttributes redirectAttributes) {
			
		      String userEmail = "N/A";

		      if (authentication != null) {
		          if (authentication instanceof OAuth2AuthenticationToken) {
		              OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		              userEmail = oauthToken.getPrincipal().getAttribute("email");
		          } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
		              UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		              userEmail = auth.getName();
		          }
		      }

		      Optional<Salon> salono = salonRepository.findById(salonId);
		      Salon salon = salono.orElseThrow(() -> new NotFoundException("Salon con ID " + salonId + " no encontrado"));

		      Reserva reserva = new Reserva();
		      reserva.setEmail(userEmail); // Asignar el email del usuario al campo email de Reserva
		      redirectAttributes.addFlashAttribute("userEmail", userEmail);
		      redirectAttributes.addFlashAttribute("salonId", salon.getId());
		      redirectAttributes.addFlashAttribute("salonNombre", salon.getNombre());
		      redirectAttributes.addFlashAttribute("salonPrecio", salon.getPrecio());
		      redirectAttributes.addFlashAttribute("reserva", reserva);
		      model.addAttribute("userEmail",userEmail);
		      model.addAttribute("salonId", salon.getId());
		      model.addAttribute("salonNombre", salon.getNombre());
		      model.addAttribute("salonPrecio", salon.getPrecio());
		      model.addAttribute("reserva", reserva); // Pasar la instancia de Reserva al modelo

		      return "Reservadd";
		  }

		   
		    
		    

		    

		    @PostMapping("/save")
		    public String reservaSaveProcess(@ModelAttribute("reserva") Reserva reserva,@RequestParam("estado") boolean estado,@RequestParam("salonid") 
		     String salonid,
		    		   @RequestParam("nombreTarjeta") String nombreTarjeta,
		    	        @RequestParam("numeroTarjeta") Long numeroTarjeta,
		    	        @RequestParam("codigoTarjeta") int codigoTarjeta,
		    	        Model model,Authentication authentication, RedirectAttributes redirectAttributes) {
		    	 String userEmail = "N/A";
		    	 
		    	 
		    	 

			      if (authentication != null) {
			          if (authentication instanceof OAuth2AuthenticationToken) {
			              OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
			              userEmail = oauthToken.getPrincipal().getAttribute("email");
			          } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
			              UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
			              userEmail = auth.getName();
			          }
			      }
		    
		    	if (reserva.getId().isEmpty()) {
		    		reserva.setId(null);
		        }
		    	
		    	 Optional<Salon> salonOptional = salonRepository.findById(salonid);
		    	 Salon salon = salonOptional.orElseThrow(() -> new NotFoundException("Salon con ID " + salonid + " no encontrado"));
		    	 
		    	 
		    	 // Lógica para validar la tarjeta
		    	    boolean tarjetaValida = tarjetaService.validarTarjeta(nombreTarjeta, numeroTarjeta, codigoTarjeta);
		    	    boolean montoSuficiente = tarjetaService.validarMontoSuficiente(numeroTarjeta, salon.getPrecio());
		    	    
		    	    if (!tarjetaValida) {
		    	    	// En el método save del controlador
		    	        return "redirect:/reservas/new?salonid=" + salonid + "&salonNombre=" + salonRepository.findById(salonid).get().getNombre()+ "&error=TARJETA INVALIDA!";
		    	    }

		    	    if (!montoSuficiente) {
		    	        return "redirect:/reservas/new?salonid=" + salonid + "&salonNombre=" + salonRepository.findById(salonid).get().getNombre()+ "&error=MONTO INSUFICIENTE!";
		    	    }
		    	 
		    	    if (reservaRepository.existsBySalonAndFecha(salon, reserva.getFecha())) {
		    	    	 return "redirect:/reservas/new?salonid=" + salonid + "&salonNombre=" + salonRepository.findById(salonid).get().getNombre()+ "&error=SALON NO DISPONIBLE EN LA FECHA SELECCIONADA!!";
		    	    }
		    	    
		    	    if (salonOptional.isPresent()) {
		    	        salon = salonOptional.get();
		    	        reserva.setSalon(salon);
		    	    } else {
		    	        // Manejar el caso en que no se encuentre el salón (puedes lanzar una excepción, redirigir, etc.)
		    	        // Por ejemplo, puedes lanzar una excepción NotFoundException.
		    	        throw new NotFoundException("Salon con ID " + salonid + " no encontrado");
		    	    }
		    	    
		    	  

  
		    		reserva.setEmail(userEmail);
		            reservaRepository.save(reserva);
		           
		            return "redirect:/reservas/reservasu";
		        
		        }
		    
		    
		    @GetMapping("/edit/{id}")
		    public String showEditForm(@PathVariable("id") String reservaId, Model model) {
		        Reserva reserva = reservaRepository.findById(reservaId)
		                .orElseThrow(() -> new NotFoundException("Reserva con ID " + reservaId + " no encontrada"));

		        model.addAttribute("reserva", reserva);
		        return "ReservaEdit";
		    }

		    
		    
		    @PostMapping("/savee")
		    public String saveEdit(@ModelAttribute("reserva") Reserva editedReserva, Model model) {
		        // Cargar la reserva existente
		        Reserva existingReserva = reservaRepository.findById(editedReserva.getId())
		                .orElseThrow(() -> new NotFoundException("Reserva con ID " + editedReserva.getId() + " no encontrada"));

		        // Actualizar solo el campo estado
		        existingReserva.setEstado(editedReserva.getEstado());

		        // Guardar la reserva actualizada
		        reservaRepository.save(existingReserva);

		        // Redireccionar a la página de detalles o a donde desees
		        return "redirect:/reservas";
		    }

		   
		    
		    
		    @GetMapping("/reservasu")
		    public String reListuTemplate(Model model,Authentication authentication) {
			  String username = "N/A";

		      if (authentication != null) {
		          if (authentication instanceof OAuth2AuthenticationToken) {
		              OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		              username = oauthToken.getPrincipal().getAttribute("email");
		          } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
		              UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		              username = auth.getName();
		          }
		      }
		      
		      List<Reserva> reservas = reservaRepository.findByEmail(username);
		      model.addAttribute("username",username);
		      model.addAttribute("reservas", reservas);
		    
		        return "Reservasu";
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
		    public String actualizarSalon(@ModelAttribute("reserva") @RequestParam ("salon") Salon salon,@RequestParam(name = "file", required = false) MultipartFile file, Reserva reserva, Model model) {
		        reserva.setSalon(salon);
		        if (reserva.getId().isEmpty()) {
		    		reserva.setId(null);
		        }
		        reservaRepository.save(reserva);

		                return "redirect:/reservas";
		            
		        
		        
		    }


		    @GetMapping("/pdf")
		    public ResponseEntity<byte[]> generatePdf() {
		        List<Reserva> reservas = reservaRepository.findAll();
		        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        
		        try {
		            byte[] pdfContent = pdfGeneratorService.generaterPdf(reservas, authentication);

		            HttpHeaders headers = new HttpHeaders();
		            headers.setContentType(MediaType.APPLICATION_PDF);
		            headers.setContentDispositionFormData("inline", "reservas.pdf");

		            return ResponseEntity.ok().headers(headers).body(pdfContent);
		        } catch (IOException e) {
		            // Manejar excepciones aquí
		            e.printStackTrace();
		            return ResponseEntity.status(500).body(null);
		        }
		    }
		    
		    
		    
			
		    @GetMapping("/delete/{id}")
		    public String reservaDeleteProcess(@PathVariable("id") String id) {
		    	reservaRepository.deleteById(id);
		        return "redirect:/reservas";
		    }
		    
		    
		    

		    @GetMapping("/deleteu/{id}")
		    public String reservaDeleteuProcess(@PathVariable("id") String id) {
		    	reservaRepository.deleteById(id);
		        return "redirect:/reservas/reservasu";
		    }
		    

		   

		    
		    
		}