package ru.learn.skill.spring.book.redis.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.learn.skill.spring.book.redis.app.entity.BookEntity;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> getBookEntityByNameAndAuthor(String name, String author);

}
