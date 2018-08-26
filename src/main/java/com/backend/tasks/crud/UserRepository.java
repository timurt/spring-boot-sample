package com.backend.tasks.crud;

import com.backend.tasks.repository.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for {@code User} class.
 *
 * @author Timur Tibeyev.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findAllByOrganizationId(Long orgId);
}
