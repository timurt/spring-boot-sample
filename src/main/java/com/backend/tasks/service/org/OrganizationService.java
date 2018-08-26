package com.backend.tasks.service.org;

import com.backend.tasks.repository.Organization;

public interface OrganizationService {
    Organization save(Organization organization);

    Organization update(Long id, Organization organization);

    Organization findById(Long id);

    boolean exists(Long id);

    void delete(Long orgId);

    Iterable<Organization> findAll();
}
