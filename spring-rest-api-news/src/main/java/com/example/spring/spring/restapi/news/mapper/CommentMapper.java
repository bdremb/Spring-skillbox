package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.web.model.request.CommentRequest;
import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, NewsItemMapper.class}
)
public interface CommentMapper {

    default Comment toModel(User user, NewsItem newsItem, CommentRequest request) {
        return Comment.builder()
                .commentText(request.getCommentText())
                .newsItem(newsItem)
                .user(user)
                .build();
    }

    default Comment toUpdateModel(Comment comment, CommentRequest request) {
        if (comment == null) {
            return null;
        }
        comment.setCommentText(request.getCommentText());
        return comment;
    }

    @Mapping(target = "newsItemId", source = "model.newsItem.id")
    CommentResponse toResponse(Comment model);

    List<CommentResponse> toResponseList(List<Comment> models);

}
