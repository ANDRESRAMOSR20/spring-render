package ecommerce.src.model;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user")
public class User {
	
	@Id
	private String id;

	private String firstName;

	private String lastName;

	private int contadorSesiones;
	
	private String email;

	private String password;

	private Collection<Role> roles;
	
	

	public User() {
		super();
	}
	
	public User(String email, Collection<Role> roles) {
		super();
		this.email = email;
		this.roles = roles;
	}

	public User( String firstName, String lastName, String email, String password, Collection<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public int getContadorSesiones() {
		return contadorSesiones;
	}

	public void setContadorSesiones(int contadorSesiones) {
		this.contadorSesiones = contadorSesiones;
	}
	
	

}
