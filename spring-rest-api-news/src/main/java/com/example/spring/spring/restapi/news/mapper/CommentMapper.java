package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.web.model.request.CommentRequest;
import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment toModel(CommentRequest request);

    Comment toModel(Long id, CommentRequest request);

    CommentResponse toResponse(Comment model);

    List<CommentResponse> toResponseList(List<Comment> models);

}
