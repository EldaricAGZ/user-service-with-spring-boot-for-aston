package com.example.web.dto;

import jakarta.validation.constraints.*;

public class UserDto {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Min(0)
    @Max(200)
    private Integer age;

    public UserDto() {
    }

    public UserDto(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
