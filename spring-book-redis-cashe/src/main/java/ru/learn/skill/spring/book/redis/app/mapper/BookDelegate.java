package ru.learn.skill.spring.book.redis.app.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import ru.learn.skill.spring.book.redis.app.entity.BookEntity;
import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;
import ru.learn.skill.spring.book.redis.app.repository.CategoryRepository;

import static java.util.Objects.nonNull;


public abstract class BookDelegate implements BookMapper {

    @Autowired
    private CategoryRepository categoryRepository;

//    @Override
//    public BookEntity toModel(BookRequest request, CategoryEntity category) {
//        return BookEntity.builder()
//                .author(request.getAuthor())
//                .name(request.getName())
//                .category(category)
//                .build();
//    }

    @Override
    public BookEntity toUpdatedModel(BookEntity model, BookRequest request) {
        categoryRepository.findByName(request.getCategoryName()).ifPresent(model::setCategory);
        if(nonNull(request.getName())) {
            model.setName(request.getName());
        }
        if(nonNull(request.getAuthor())) {
            model.setAuthor(request.getAuthor());
        }
        return model;
    }

}
