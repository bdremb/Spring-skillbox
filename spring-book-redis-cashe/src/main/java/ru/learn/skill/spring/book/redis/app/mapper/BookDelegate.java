package ru.learn.skill.spring.book.redis.app.mapper;

import ru.learn.skill.spring.book.redis.app.entity.BookEntity;
import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;

import static java.util.Objects.nonNull;


public abstract class BookDelegate implements BookMapper {

    @Override
    public BookEntity toUpdatedModel(BookEntity model, BookRequest request) {
        if (nonNull(request.getName())) {
            model.setName(request.getName());
        }
        if (nonNull(request.getAuthor())) {
            model.setAuthor(request.getAuthor());
        }
        return model;
    }

}
