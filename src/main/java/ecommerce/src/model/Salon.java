package ecommerce.src.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document(collection = "salon")
public class Salon {

	@Id
	private String id;
	
	private String nombre;
	
	private byte[] imagen;
	
	private int precio;
	
	private String barrio;
	
	@DocumentReference
	private List<Elementos> elementos;

	private String direccion;
	
	private int capacidad;
	
	private String Disponibilidad;
	
	private String Ubicacion; 

	public Salon() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String getDisponibilidad() {
		return Disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		Disponibilidad = disponibilidad;
	}

	public String getUbicacion() {
		return Ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		Ubicacion = ubicacion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
	public List<Elementos> getElementos() {
		return elementos;
	}

	public void setElementos(List<Elementos> elementos) {
		this.elementos = elementos;
	}
	
	
	
	
}