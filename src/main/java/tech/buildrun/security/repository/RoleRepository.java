package tech.buildrun.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.security.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
