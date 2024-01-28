package com.example.spring.demojooq.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class Task {

    private Long id;
    private String title;
    private String description;
    private int priority;
}
