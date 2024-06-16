package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.web.model.request.UserRequest;
import com.example.spring.spring.restapi.news.web.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewsItemMapper.class}
)
public interface UserMapper {

    User toModel(UserRequest request);

       User toModel(Long id, UserRequest request);

    UserResponse toResponse(User model);

    List<UserResponse> toResponseList(List<User> models);

    default User toUpdatedModel(User user, UserRequest request) {
        user.setUsername(request.getUsername());
        return user;
    }
}
