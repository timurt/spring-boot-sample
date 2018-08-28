package com.backend.tasks.crud;

import com.backend.tasks.repository.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for {@code User} class.
 *
 * @author Timur Tibeyev.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Select query - selects users by organization id.
     * Equivalent to `select * from users where org_id = ?;`
     *
     * @param orgId the organization id
     * @return list of users
     */
    Iterable<User> findAllByOrganizationId(Long orgId);
}
