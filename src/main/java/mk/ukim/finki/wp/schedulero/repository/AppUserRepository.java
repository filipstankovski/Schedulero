package mk.ukim.finki.wp.schedulero.repository;

import mk.ukim.finki.wp.schedulero.enums.UserRole;
import mk.ukim.finki.wp.schedulero.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<AppUser> findByRole(UserRole role);
}