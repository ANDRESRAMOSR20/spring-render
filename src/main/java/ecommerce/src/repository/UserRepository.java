package ecommerce.src.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ecommerce.src.model.User;

public interface UserRepository extends MongoRepository<User, String>{
	User findByEmail(String email);
}
