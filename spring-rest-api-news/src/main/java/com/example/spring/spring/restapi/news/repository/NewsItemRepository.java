package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.NewsItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long>, JpaSpecificationExecutor<NewsItem> {

    @Override
    @EntityGraph(attributePaths = {"comments"})
    Optional<NewsItem> findById(Long id);
}
