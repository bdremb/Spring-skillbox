package com.example.spring.springdatacontacts.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class Contact {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
