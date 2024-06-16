package com.example.spring.spring.restapi.news.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OwnerVerification {

    EntityType entityType();

    String pathVariableIdName() default "id";
}
