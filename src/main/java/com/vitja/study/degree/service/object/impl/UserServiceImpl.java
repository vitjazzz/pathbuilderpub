package com.vitja.study.degree.service.object.impl;


import com.vitja.study.degree.exception.UserNotFoundException;
import com.vitja.study.degree.model.Role;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.repository.UserRepository;
import com.vitja.study.degree.service.object.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void updateToManager(String id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() ->  new UserNotFoundException("User with such ID not found."));
        if(user.get().getRole().equals(Role.CUSTOMER)){
            user.get().setRole(Role.MANAGER);
        }
        userRepository.save(user.get());
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(UUID.randomUUID().toString());
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException("User with such ID not found.");
        }

    }
}
