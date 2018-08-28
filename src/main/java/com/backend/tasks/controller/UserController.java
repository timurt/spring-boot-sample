package com.backend.tasks.controller;

import com.backend.tasks.repository.User;
import com.backend.tasks.service.org.OrganizationService;
import com.backend.tasks.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implement create, read, update, delete  rest controller endpoints for user.
 * Map endpoints to /orgs/{orgId}/users path
 * 1. Post to /orgs/{orgId}/users endpoint should create and return user for user with id=orgId.
 * Response status should be 201.
 * 2. Put to /orgs/{orgId}/users/{userId} endpoint should update, save and return user with
 * id=userId for user with id=orgId.
 * 3. Get to /orgs/{orgId}/users/{userId} endpoint should fetch and return user with id=userId
 * for user with id=orgId.
 * 4. Delete to /orgs/{orgId}/users/{userId} endpoint should delete user with id=userId for user
 * with id=orgId. Response status should be 204.
 * 5. Get to /orgs/{orgId}/users endpoint should return list of all users for user with id=orgId
 */
@RestController
@RequestMapping("/orgs/{orgId}/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OrganizationService organizationService;

    /**
     * Creates new {@code User} object for specified organization.
     *
     * @param orgId the organization id
     * @param input the new {@code User} object
     * @return {@code ResponseEntity} object
     */
    @PostMapping
    ResponseEntity<User> save(@PathVariable final Long orgId,
                              @RequestBody final User input) {
        if (!organizationService.exists(orgId)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (userService.exists(input.getId(), orgId)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            final User saved = userService.save(orgId, input);
            return new ResponseEntity(saved, HttpStatus.CREATED);
        }
    }

    /**
     * Updates user by user id and organization id.
     *
     * @param orgId the organization id
     * @param userId the user id
     * @param input the {@code User} object with updates
     * @return {@code ResponseEntity} object
     */
    @PutMapping("/{userId}")
    ResponseEntity<User> update(@PathVariable final Long orgId,
                                @PathVariable final Long userId,
                                @RequestBody final User input) {
        if (!organizationService.exists(orgId)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (userService.exists(userId, orgId)) {
            final User saved = userService.update(orgId, userId, input);
            return new ResponseEntity(saved, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Finds user by user id and organization id.
     *
     * @param orgId the organization id
     * @param userId the user id
     * @return {@code ResponseEntity} object
     */
    @GetMapping("/{userId}")
    ResponseEntity<User> get(@PathVariable final Long orgId,
                             @PathVariable final Long userId) {
        if (!organizationService.exists(orgId)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (userService.exists(userId, orgId)) {
            return new ResponseEntity(userService.findById(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes user by user id and organization id.
     *
     * @param orgId the organization id
     * @param userId the user id
     * @return {@code ResponseEntity} object
     */
    @DeleteMapping("/{userId}")
    ResponseEntity delete(@PathVariable final Long orgId,
                          @PathVariable final Long userId) {
        if (userService.exists(userId, orgId)) {
            userService.delete(userId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns list of all users of the specified organization.
     *
     * @param orgId the organization id
     * @return {@code ResponseEntity} object
     */
    @GetMapping
    public ResponseEntity getAll(@PathVariable final Long orgId) {
        if (!organizationService.exists(orgId)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        final Iterable<User> list = userService.findAll(orgId);
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
