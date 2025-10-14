package org.vitacare.repository;

import org.vitacare.entity.User;

public interface UserRepository {
    User findByEmail(String email);
    void save(User user);
}
