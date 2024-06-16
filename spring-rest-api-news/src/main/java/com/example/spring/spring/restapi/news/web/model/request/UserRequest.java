package com.example.spring.spring.restapi.news.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    @NotNull
    @Length(max = 255, min = 2)
    private String username;

    @NotBlank
    @NotNull
    @Length(max = 255, min = 2)
    private String password;
}
