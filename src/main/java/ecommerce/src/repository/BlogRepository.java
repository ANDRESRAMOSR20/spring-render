package ecommerce.src.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ecommerce.src.model.Blog;

public interface BlogRepository extends MongoRepository<Blog,String>{

}
