package store.backend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.backend.security.role.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Long> {

}
