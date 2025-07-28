package org.example.repository;

import org.example.generics.BaseRepository;
import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends BaseRepository<User, String> {
    Optional<User> findByUsername(String username);
}
