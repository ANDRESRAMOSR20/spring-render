package ecommerce.src.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="inquietud")
public class Inquietud {

	@Id
	private String id;
	
	private String emaili;
	
	private String tipo;
	
	private String texto;

	public Inquietud() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmaili() {
		return emaili;
	}

	public void setEmaili(String emaili) {
		this.emaili = emaili;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
	
	
}
