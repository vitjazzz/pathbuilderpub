package com.vitja.study.degree.dto;


import com.vitja.study.degree.model.Role;

public class UserDTO {
    public UserDTO() {
    }

    public UserDTO(String id, String email, String firstName, String lastName, String password, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public String id;

    public String email;

    public String firstName;

    public String lastName;

    public String password;

    public Role role;
}
