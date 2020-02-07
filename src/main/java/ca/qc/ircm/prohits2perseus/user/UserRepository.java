package ca.qc.ircm.prohits2perseus.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByUsername(String username);
}
