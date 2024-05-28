package ecommerce.src.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ecommerce.src.model.Salon;


public interface SalonRepository extends MongoRepository<Salon,String>{
	@Query("{ 'Ubicacion' : ?0 }")
	List<Salon> findByUbicacion(String Ubicacion);
    

	Salon deleteByNombre(String nombre);
	@Query(value = "{}", fields = "{ 'Ubicacion' : 1 }")
    List<Salon> findAllWithUbicacion();

    default List<String> findDistinctUbicacion() {
        return findAllWithUbicacion().stream()
                .map(Salon::getUbicacion)
                .distinct()
                .collect(Collectors.toList());
    }

}