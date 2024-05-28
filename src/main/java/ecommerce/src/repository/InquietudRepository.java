package ecommerce.src.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ecommerce.src.model.Inquietud;

public interface InquietudRepository extends MongoRepository<Inquietud, String>{

}