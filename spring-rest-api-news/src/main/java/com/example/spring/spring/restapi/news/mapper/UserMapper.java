package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.Role;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.web.model.request.UserRequest;
import com.example.spring.spring.restapi.news.web.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewsItemMapper.class}
)
public interface UserMapper {

    User toModel(UserRequest request);

    default User toModel(UserRequest request, Role role) {
        User user = toModel(request);
        user.setRoles(Collections.singletonList(role));
        role.setUser(user);
        return user;
    }

    User toModel(Long id, UserRequest request);

    UserResponse toResponse(User model);

    List<UserResponse> toResponseList(List<User> models);

    default User toUpdatedModel(User user, UserRequest request) {
        user.setName(request.getName());
        return user;
    }
}
