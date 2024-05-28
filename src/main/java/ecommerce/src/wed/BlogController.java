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

import ecommerce.src.model.Blog;
import ecommerce.src.model.Elementos;
import ecommerce.src.model.Salon;
import ecommerce.src.model.User;
import ecommerce.src.repository.BlogRepository;
import ecommerce.src.repository.ElementoRepository;

@Controller
@RequestMapping(value = "blogs")
public class BlogController {
	
	@Autowired
    private BlogRepository blogRepository;

	  @GetMapping("")
	    public String salonListTemplate(Model model) {


	        model.addAttribute("blogs", blogRepository.findAll());
	    
	        return "blogs";
	    }
	  
	  
	  
	  
	    @GetMapping("/new")
	    public String salonNewTemplate(Model model) {
	    	model.addAttribute("blog", new Blog());
	   
	       
	        return "blogadd";
	    }
	  
	    
	    
	    @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable String id, Model model) {
	        Optional<Blog> opsalon = blogRepository.findById(id);
	        if (opsalon.isPresent()) {
	          Blog blog = opsalon.get();
	            model.addAttribute("blog", blog);
	            return "blogEdit";  // Nombre de tu vista de formulario de edición
	        } else {
	            return "redirect:/error";
	        }
	    }
	
	    
	    
	    @PostMapping("/save")
	    public String EstudianteSaveProcess(@ModelAttribute("blog") Blog blog, @RequestParam("file") MultipartFile file, Model model) {
	    	if (blog.getId().isEmpty()) {
	    		blog.setId(null);
	        }
	    	
	    	try {
	            // Verificar si se ha proporcionado una imagen
	            if (!file.isEmpty()) {
	                // Convertir la imagen a bytes y asignarla al salón
	                blog.setImagen(file.getBytes());
	            }

	            // Procesar y guardar el salón, incluida la imagen
	            blogRepository.save(blog);
	            return "redirect:/blogs";
	        } catch (IOException e) {
	            // Manejar la excepción en caso de error al procesar la imagen
	            e.printStackTrace();
	            return "redirect:/error";
	        }
	    }
	    
	    
	    
	    
	    @GetMapping("/delete/{id}")
	    public String salonDeleteProcess(@PathVariable("id") String id) {
	    	blogRepository.deleteById(id);
	        return "redirect:/blogs";
	    }
	
	    
	    @GetMapping("/VerImagen/{id}")
	    public ResponseEntity<byte[]> VerImagen(@PathVariable String id){
	    	
	    	Optional<Blog> opsalon = blogRepository.findById(id);
	    	
	    	if(opsalon.isPresent()) {
	    		Blog blog = opsalon.get();
	    		byte[] imagenBytes = blog.getImagen();
	    		
	    		
	    		HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.IMAGE_JPEG);
	    	
	    	return new ResponseEntity<>(imagenBytes,headers,HttpStatus.OK);
	    	
	    	}else {
	    	return ResponseEntity.notFound().build();
	    	}

	    	
	    }
	    
	    

	    @PostMapping("/update")
	    public String actualizarSalon(@ModelAttribute("blog") @RequestParam(name = "file", required = false) MultipartFile file, Blog blog, Model model) {
	    	try {
	            Optional<Blog> opsalon = blogRepository.findById(blog.getId());

	            if (opsalon.isPresent()) {
	            	Blog blogExistente = opsalon.get();
	                model.addAttribute("blog", blog);

	                // Verificar si se ha proporcionado una nueva imagen
	                if (file != null && !file.isEmpty()) {
	                    // Convertir la nueva imagen a bytes y actualizar el salón
	                	blogExistente.setImagen(file.getBytes());
	                }

	                // Actualizar otros campos del salón
	                blogExistente.setTitulo(blog.getTitulo());
	                blogExistente.setTexto(blog.getTexto());
	                blogExistente.setFecha(blog.getFecha());



	                
	                
	                // Guardar el salón actualizado
	                blogRepository.save(blogExistente);

	                return "redirect:/blogs";
	            } else {
	                return "redirect:/blogs";
	            }
	        } catch (IOException e) {
	            // Manejar la excepción en caso de error al procesar la imagen
	            e.printStackTrace();
	            return "redirect:/error";
	        }
	    }
	    
	    
	    
	    @GetMapping("/verblogs")
	    public String verCatalogo(Model model, HttpSession session, Authentication authentication) {
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
	    	  List<Blog> blogs;

	    	  blogs = blogRepository.findAll();
	    	  
	    	model.addAttribute("username", username);
	        model.addAttribute("blogs", blogs);
	        return "BlogUser";
	    }

	    
	    @GetMapping("/userblogs")
	    public String verC(Model model) {	
	        model.addAttribute("blogs", blogRepository.findAll());
	        return "blogsN";
	    }


	    

	    @GetMapping("/blogsA")
	    public String verCv(Model model) {	
	        model.addAttribute("blogs", blogRepository.findAll());
	        return "blogsA";
	    }
	
}