package ru.learn.skill.spring.flux.task.tracker.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleType {
    ROLE_USER,
    ROLE_MANAGER;

    public static GrantedAuthority toAuthority(RoleType roleType) {
        return new SimpleGrantedAuthority(roleType.name());
    }
}
