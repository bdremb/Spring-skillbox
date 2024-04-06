package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);

    @Override
    @EntityGraph(attributePaths = {"newsItems"})
    Optional<Category> findById(Long id);
}
