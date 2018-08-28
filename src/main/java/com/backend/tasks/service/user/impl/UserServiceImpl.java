package com.backend.tasks.service.user.impl;

import com.backend.tasks.crud.OrganizationRepository;
import com.backend.tasks.crud.UserRepository;
import com.backend.tasks.repository.Organization;
import com.backend.tasks.repository.User;
import com.backend.tasks.service.user.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    OrganizationRepository organizationRepository;

    /**
     * Saves new {@code User} object in database.
     *
     * @param orgId the organization id
     * @param user the new {@code User} object
     * @return newly created object
     */
    @Override
    public User save(Long orgId, User user) {
        final Organization organization = organizationRepository.findById(orgId).get();

        user.setOrganization(organization);

        return repository.save(user);
    }

    /**
     * Updates existing {@code User} object in database.
     *
     * @param orgId the organization id
     * @param userId the user id
     * @param user the {@code User} object with updates
     * @return updated object
     */
    @Override
    public User update(Long orgId, Long userId, User user) {
        final Organization organization = organizationRepository.findById(orgId).get();

        user.setId(userId);
        user.setOrganization(organization);

        return repository.save(user);
    }

    /**
     * Selects {@code User} object from database by id.
     *
     * @param id the user id
     * @return selected object
     */
    @Override
    public User findById(Long id) {
        Optional<User> opt = repository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return null;
        }
    }

    /**
     * Checks if user with specified id and organization id exists or not.
     *
     * @param id the user id
     * @param orgId the organization id
     * @return true if organization exists, false otherwise
     */
    @Override
    public boolean exists(Long id, Long orgId) {
        if (id == null) {
            return false;
        }
        Optional<User> optional = repository.findById(id);
        if (optional.isPresent()) {
            Organization org = optional.get().getOrganization();
            return org != null && org.getId().equals(orgId);
        } else {
            return false;
        }
    }

    /**
     * Deletes {@code User} object from database by id.
     *
     * @param userId the user id
     */
    @Override
    public void delete(Long userId) {
        repository.deleteById(userId);
    }

    /**
     * Selects all {@code User} objects with specified organization id from database.
     *
     * @param orgId the organization id
     * @return list of users
     */
    @Override
    public Iterable<User> findAll(Long orgId) {
        return repository.findAllByOrganizationId(orgId);
    }
}
