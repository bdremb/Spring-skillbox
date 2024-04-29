package ru.learn.skill.spring.book.redis.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.learn.skill.spring.book.redis.app.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(String name);
}
