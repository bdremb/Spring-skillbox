package ru.learn.skill.spring.flux.task.tracker.web.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.RoleType;
import ru.learn.skill.spring.flux.task.tracker.service.UserService;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.UserRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.UserResponse;


@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;

    @PostMapping("/account")
    public Mono<ResponseEntity<UserResponse>> createUserAccount(@RequestBody UserRequest userDto, @RequestParam RoleType roleType) {
        return userService.createNewAccount(userDto, roleType).map(ResponseEntity::ok);
    }

}
