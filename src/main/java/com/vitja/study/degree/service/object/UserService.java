package com.vitja.study.degree.service.object;

import com.vitja.study.degree.model.User;

import java.util.Optional;

public interface UserService {
    User save(User user);
    void updateToManager(String id);
    Optional<User> getUser(String id);
    Optional<User> getUserByEmail(String email);
    void deleteUser(String id);
}
