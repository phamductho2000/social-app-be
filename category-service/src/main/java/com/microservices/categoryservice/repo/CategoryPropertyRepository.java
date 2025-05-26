package com.microservices.categoryservice.repo;

import com.microservices.categoryservice.domain.Category;
import com.microservices.categoryservice.domain.CategoryProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryPropertyRepository extends MongoRepository<CategoryProperty, String> {
}
