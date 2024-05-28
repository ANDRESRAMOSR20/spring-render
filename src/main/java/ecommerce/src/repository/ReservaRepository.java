package ecommerce.src.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ecommerce.src.model.Reserva;
import ecommerce.src.model.Salon;

public interface ReservaRepository extends MongoRepository<Reserva ,String> {


	boolean existsBySalonAndFecha(Salon salon, Date fecha);

	List<Reserva> findByEmail(String email);

}