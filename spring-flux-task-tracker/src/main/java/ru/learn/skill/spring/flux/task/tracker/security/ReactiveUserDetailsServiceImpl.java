package ru.learn.skill.spring.flux.task.tracker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.service.UserService;

@Service
@RequiredArgsConstructor
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.findUserByUsername(username)
                .flatMap(Mono::just)
                .map(AppUserPrincipal::new);
    }

}
