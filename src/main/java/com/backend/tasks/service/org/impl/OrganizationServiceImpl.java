package com.backend.tasks.service.org.impl;

import com.backend.tasks.crud.OrganizationRepository;
import com.backend.tasks.repository.Organization;
import com.backend.tasks.service.org.OrganizationService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationRepository repository;

    /**
     * Saves new {@code Organization} object in database.
     *
     * @param organization the new {@code Organization} object
     * @return newly created object
     */
    @Override
    public Organization save(Organization organization) {
        return repository.save(organization);
    }

    /**
     * Updates existing {@code Organization} object in database.
     *
     * @param id the organization id
     * @param input the {@code Organization} object with updates
     * @return updated object
     */
    @Override
    public Organization update(Long id, Organization input) {
        input.setId(id);
        return repository.save(input);
    }

    /**
     * Selects {@code Organization} object from database by id.
     *
     * @param id the organization id
     * @return selected object
     */
    @Override
    public Organization findById(Long id) {
        Optional<Organization> opt = repository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return null;
        }
    }

    /**
     * Checks if organization with specified id exists or not.
     *
     * @param id the organization id
     * @return true if organization exists, false otherwise
     */
    @Override
    public boolean exists(final Long id) {
        if (id == null) {
            return false;
        }
        Optional<Organization> optional = repository.findById(id);
        return optional.isPresent();
    }

    /**
     * Deletes {@code Organization} object from database by id.
     *
     * @param orgId the organization id
     */
    @Override
    public void delete(Long orgId) {
        repository.deleteById(orgId);
    }

    /**
     * Selects all {@code Organization} objects from database.
     *
     * @return list of organizations
     */
    @Override
    public Iterable<Organization> findAll() {
        return repository.findAll();
    }
}
