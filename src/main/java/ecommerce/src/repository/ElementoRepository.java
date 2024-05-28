package ecommerce.src.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import ecommerce.src.model.Elementos;

public interface ElementoRepository extends MongoRepository<Elementos,String>{
	
}