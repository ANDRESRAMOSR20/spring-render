package ecommerce.src.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


import ecommerce.src.model.Tarjeta;

public interface TarjetaRepository extends MongoRepository<Tarjeta,String>{
	Optional<Tarjeta>findByNombre(String nombre);
	Tarjeta findByNumero(Long numero);
	Boolean existsByNombreAndNumeroAndCodigo(String nombre, Long numero, int codigo);
}
