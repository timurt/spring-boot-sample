package com.backend.tasks.service.user;

import com.backend.tasks.repository.User;

public interface UserService {
    User save(Long orgId, User user);

    User update(Long orgId, Long userId, User user);

    User findById(Long id);

    boolean exists(Long id, Long orgId);

    void delete(Long orgId);

    Iterable<User> findAll(Long orgId);
}
