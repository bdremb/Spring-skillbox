package com.example.spring.spring.restapi.news.aop;

import com.example.spring.spring.restapi.news.exception.EntityNotFoundException;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;
import com.example.spring.spring.restapi.news.web.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.spring.spring.restapi.news.aop.Action.DELETE;
import static com.example.spring.spring.restapi.news.aop.Action.UPDATE;
import static com.example.spring.spring.restapi.news.model.RoleType.ROLE_ADMIN;
import static com.example.spring.spring.restapi.news.model.RoleType.ROLE_MODERATOR;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OwnerVerificationAspect {

    private final AggregateRepository aggregateRepository;
    private final ObjectMapper objectMapper;

    @Around("@annotation(OwnerVerification) && execution(* * (@org.springframework.security.core.annotation.AuthenticationPrincipal (*),..)) && args(object,..)")
    public Object validateBefore(ProceedingJoinPoint joinPoint, Object object) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Before execution of: {}", methodSignature.getName());

        final OwnerVerification annotation = methodSignature.getMethod().getAnnotation(OwnerVerification.class);
        final ObjectInfo objectInfo = getObjectInfo(annotation, object);
        final EntityType entityType = annotation.entityType();
        if (isNotUserNameMatched(objectInfo, entityType, annotation.action())) {
            return ResponseEntity.status(FORBIDDEN)
                    .body(ErrorResponse.builder()
                            .errorMessage(String.format(
                                    "Forbidden request. User with name = %s is not owner of the %s with id=%d",
                                    objectInfo.getUsername(),
                                    entityType,
                                    objectInfo.getEntityId()
                            )).build());
        }
        return joinPoint.proceed();
    }

    protected boolean isNotUserNameMatched(ObjectInfo objectInfo, EntityType entityType, Action action) {
        final Pair<Long, Long> entityIdUserId = getEntityIdUserId(objectInfo);
        return switch (entityType) {
            case NEWS -> {
                if (action != UPDATE && roleHasPermission(objectInfo)) {
                    yield false;
                }
                yield aggregateRepository.getNewsItemRepository()
                        .findByIdAndUserId(entityIdUserId.getLeft(), entityIdUserId.getRight())
                        .isEmpty();
            }
            case COMMENT -> {
                if (action != UPDATE && roleHasPermission(objectInfo)) {
                    yield false;
                }
                if (action == DELETE && roleHasPermission(objectInfo)) {
                    yield false;
                }
                yield aggregateRepository.getCommentRepository()
                        .findByIdAndUserId(entityIdUserId.getLeft(), entityIdUserId.getRight())
                        .isEmpty();
            }
            case USER -> {
                if (roleHasPermission(objectInfo)) {
                    yield false;
                }
                yield aggregateRepository.getUserRepository()
                        .findUserByIdAndUsername(entityIdUserId.getLeft(), objectInfo.getUsername())
                        .isEmpty();
            }
        };
    }

    private boolean roleHasPermission(ObjectInfo objectInfo) {
        return objectInfo.roles.contains(ROLE_ADMIN.name()) || objectInfo.roles.contains(ROLE_MODERATOR.name());
    }

    private Pair<Long, Long> getEntityIdUserId(ObjectInfo objectInfo) {
        String userName = objectInfo.getUsername();
        return Pair.of(
                objectInfo.getEntityId(),
                aggregateRepository.getUserRepository().findByUsername(userName)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("User with name=%s not found", userName)))
                        .getId()
        );
    }

    @SneakyThrows
    private ObjectInfo getObjectInfo(OwnerVerification annotation, Object object) {
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(object));
        RequestAttributes requestAttributes = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return ObjectInfo.builder()
                .username(jsonNode.findValue("username").asText())
                .entityId(Long.valueOf(pathVariables.get(annotation.pathVariableIdName())))
                .roles(jsonNode.findValue("authorities").findValuesAsText("authority"))
                .build();
    }

    @Data
    @Builder
    private static class ObjectInfo {
        private String username;
        private List<String> roles;
        private Long entityId;
    }

}

