package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.NewsItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long>, JpaSpecificationExecutor<NewsItem> {

    @Override
    @EntityGraph(attributePaths = {"comments", "user"})
    Optional<NewsItem> findById(Long id);

    NewsItem findByIdAndUserId(Long id, Long userId);

    @Override
    @EntityGraph(attributePaths = {"comments", "user", "category"})
    Page<NewsItem> findAll(Specification<NewsItem> spec, Pageable pageable);
}
