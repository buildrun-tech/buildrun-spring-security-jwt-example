package tech.buildrun.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.security.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
