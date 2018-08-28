package com.backend.tasks.controller;

import com.backend.tasks.repository.Organization;
import com.backend.tasks.service.org.OrganizationService;
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
 * Implement create, read, update, delete  rest controller endpoints for organization.
 * Map endpoints to /orgs path.
 * 1. Post to /orgs endpoint should create and return organization. Response status should be 201.
 * 2. Put to /orgs/{orgId} endpoint should update, save and return organization with id=orgId.
 * 3. Get to /orgs/{orgId} endpoint should fetch and return organization with id=orgId.
 * 4. Delete to /orgs/{orgId} endpoint should delete organization with id=orgId. Response status
 * should be 204.
 * 5. Get to /orgs endpoint should return list of all organizations
 */
@RestController
@RequestMapping("/orgs")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    /**
     * Creates new {@code Organization} object.
     *
     * @param input the new {@code Organization} object
     * @return {@code ResponseEntity} object
     */
    @PostMapping
    ResponseEntity<Organization> save(@RequestBody final Organization input) {
        if (organizationService.exists(input.getId())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            final Organization saved = organizationService.save(input);
            return new ResponseEntity(saved, HttpStatus.CREATED);
        }
    }

    /**
     * Updates organization by id.
     *
     * @param orgId the organization id
     * @param input the {@code Organization} object with updates
     * @return {@code ResponseEntity} object
     */
    @PutMapping("/{orgId}")
    ResponseEntity<Organization> update(@PathVariable final Long orgId,
                                        @RequestBody final Organization input) {
        if (organizationService.exists(orgId)) {
            final Organization saved = organizationService.update(orgId, input);
            return new ResponseEntity(saved, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Finds organization by id.
     *
     * @param orgId the organization id
     * @return {@code ResponseEntity} object
     */
    @GetMapping("/{orgId}")
    ResponseEntity<Organization> get(@PathVariable final Long orgId) {
        if (organizationService.exists(orgId)) {
            return new ResponseEntity(organizationService.findById(orgId), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes organization with specified id.
     *
     * @param orgId the organization id
     * @return {@code ResponseEntity} object
     */
    @DeleteMapping("/{orgId}")
    ResponseEntity delete(@PathVariable final Long orgId) {
        if (organizationService.exists(orgId)) {
            organizationService.delete(orgId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns list of all organizations.
     *
     * @return {@code ResponseEntity} object
     */
    @GetMapping
    public ResponseEntity getAll() {
        final Iterable<Organization> list = organizationService.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
