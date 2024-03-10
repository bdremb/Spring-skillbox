package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long>, JpaSpecificationExecutor<NewsCategory> {

}
