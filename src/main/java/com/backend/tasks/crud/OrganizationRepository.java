package com.backend.tasks.crud;

import com.backend.tasks.repository.Organization;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for {@code Organization} class.
 *
 * @author Timur Tibeyev.
 */
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
}
