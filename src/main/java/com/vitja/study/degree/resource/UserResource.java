package com.vitja.study.degree.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vitja.study.degree.model.User;
import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {
    private final User user;

    @JsonCreator
    public UserResource(@JsonProperty("user") User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
