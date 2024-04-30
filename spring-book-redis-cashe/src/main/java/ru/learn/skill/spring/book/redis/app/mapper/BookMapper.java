package ru.learn.skill.spring.book.redis.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.learn.skill.spring.book.redis.app.entity.BookEntity;
import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;
import ru.learn.skill.spring.book.redis.app.model.response.BookResponse;

import java.util.List;

import static java.util.Objects.nonNull;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookMapper {

    BookEntity toModel(BookRequest request);

    default BookEntity toUpdatedModel(BookEntity model, BookRequest request) {
        if (nonNull(request.getName())) {
            model.setName(request.getName());
        }
        if (nonNull(request.getAuthor())) {
            model.setAuthor(request.getAuthor());
        }
        if (nonNull(request.getInfo())) {
            model.setInfo(request.getInfo());
        }
        return model;
    }

    @Mapping(target = "categoryName", source = "model.category.name")
    BookResponse toResponse(BookEntity model);

    List<BookResponse> toResponseList(List<BookEntity> models);

}
