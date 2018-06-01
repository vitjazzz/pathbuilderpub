package com.vitja.study.degree.controller;

import com.vitja.study.degree.exception.UserNotFoundException;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.service.object.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@RestController
@RequestMapping("/degree/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<User> createCustomer(@RequestBody User user) {
        User createdUser = userService.save(user);

        return ResponseEntity.ok(createdUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Void> updateToManager(@PathVariable String id) {
        userService.updateToManager(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location",linkTo(UserController.class).slash(id).withSelfRel().getHref());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String id) {
        Optional<User> user = userService.getUser(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        return ResponseEntity.ok(user.get());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> getLoggedInUser(Principal principal) {
        Optional<User> user = userService.getUserByEmail(principal.getName());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found with username: " + principal.getName());
        }
        return ResponseEntity.ok(user.get());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
