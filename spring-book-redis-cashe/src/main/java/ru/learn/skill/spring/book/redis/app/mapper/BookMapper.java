package ru.learn.skill.spring.book.redis.app.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.learn.skill.spring.book.redis.app.entity.BookEntity;
import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;
import ru.learn.skill.spring.book.redis.app.model.response.BookResponse;

import java.util.List;

@DecoratedWith(BookDelegate.class)
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookMapper {

    BookEntity toModel(BookRequest request);

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "author", source = "request.author")
    BookEntity toUpdatedModel(BookEntity model, BookRequest request);

    @Mapping(target = "categoryName", source = "model.category.name")
    BookResponse toResponse(BookEntity model);

    List<BookResponse> toResponseList(List<BookEntity> models);

}
