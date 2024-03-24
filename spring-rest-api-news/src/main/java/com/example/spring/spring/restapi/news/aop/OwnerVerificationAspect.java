package com.example.spring.spring.restapi.news.aop;

import com.example.spring.spring.restapi.news.exception.EntityNotFoundException;
import com.example.spring.spring.restapi.news.repository.CommentRepository;
import com.example.spring.spring.restapi.news.repository.NewsItemRepository;
import com.example.spring.spring.restapi.news.repository.UserRepository;
import com.example.spring.spring.restapi.news.web.model.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OwnerVerificationAspect {

    private final CommentRepository commentRepository;
    private final NewsItemRepository newsItemRepository;
    private final UserRepository userRepository;

    @Around("@annotation(OwnerVerification)")
    public Object validateBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Before execution of: {}", methodSignature.getName());

        final OwnerVerification annotation = methodSignature.getMethod().getAnnotation(OwnerVerification.class);
        final Pair<Long, String> entityIdUserName = getRequestedEntityIdUserName(annotation);
        final EntityType entityType = annotation.entityType();
        if (isNotUserNameMatched(entityIdUserName, entityType)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ErrorResponse.builder()
                            .message(String.format(
                                    "Forbidden request. User with name = %s is not owner of the %s with id=%d",
                                    entityIdUserName.getRight(),
                                    entityType,
                                    entityIdUserName.getLeft()
                            )).build());
        }
        return joinPoint.proceed();
    }

    protected boolean isNotUserNameMatched(Pair<Long, String> entityIdUserNamePair, EntityType entityType) {
        final Pair<Long, Long> entityIdUserId = getEntityIdUserId(entityIdUserNamePair);
        switch (entityType) {
            case NEWS -> {
                return isNull(newsItemRepository.findByIdAndUserId(entityIdUserId.getLeft(), entityIdUserId.getRight()));
            }
            case COMMENT -> {
                return isNull(commentRepository.findByIdAndUserId(entityIdUserId.getLeft(), entityIdUserId.getRight()));
            }
            default -> {
                return true;
            }
        }
    }

    private Pair<Long, Long> getEntityIdUserId(Pair<Long, String> entityIdUserNamePair) {
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

