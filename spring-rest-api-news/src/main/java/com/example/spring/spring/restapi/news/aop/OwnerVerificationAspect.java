package com.example.spring.spring.restapi.news.aop;

import com.example.spring.spring.restapi.news.exception.EntityNotFoundException;
import com.example.spring.spring.restapi.news.repository.CommentRepository;
import com.example.spring.spring.restapi.news.repository.NewsItemRepository;
import com.example.spring.spring.restapi.news.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

import static java.util.Objects.nonNull;
import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OwnerVerificationAspect {

    private final CommentRepository commentRepository;
    private final NewsItemRepository newsItemRepository;
    private final UserRepository userRepository;

    @Before("@annotation(OwnerVerification)")
    public void logBefore(JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Before execution of: {}", methodSignature.getName());

        final OwnerVerification annotation = methodSignature.getMethod().getAnnotation(OwnerVerification.class);

        if(!isUserNameMatched(annotation)) {
            System.err.println("USER NOT OWNER");
        }

    }

    protected boolean isUserNameMatched(OwnerVerification annotation) {
        EntityType entityType = annotation.entityType();
        final Pair<Long, Long> entityIdUserId = getEntityIdUserId(annotation);
        switch (entityType) {
            case NEWS -> {
                return nonNull(newsItemRepository.findByIdAndUserId(entityIdUserId.getLeft(), entityIdUserId.getRight()));
            }
            case COMMENT -> {
                return nonNull(commentRepository.findByIdAndUserId(entityIdUserId.getLeft(), entityIdUserId.getRight()));
            }
            default -> {
                return false;
            }
        }
    }

    private Pair<Long, Long> getEntityIdUserId(OwnerVerification annotation) {
        final Pair<Long, String> entityIdUserNamePair = getRequestedEntityIdUserName(annotation);
        String userName = entityIdUserNamePair.getRight();
        return Pair.of(
                entityIdUserNamePair.getKey(),
                userRepository.findByName(userName)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("User with name=%s not found", userName)))
                        .getId()
        );
    }

    private Pair<Long, String> getRequestedEntityIdUserName(OwnerVerification annotation) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        final Long entityId = Long.valueOf(pathVariables.get(annotation.pathVariableIdName()));
        final String userName = pathVariables.get(annotation.pathVariableName());
        return Pair.of(entityId, userName);
    }

}

