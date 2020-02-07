package ca.qc.ircm.prohits2perseus.security;

import ca.qc.ircm.prohits2perseus.project.Project;
import ca.qc.ircm.prohits2perseus.project.ProjectData;
import ca.qc.ircm.prohits2perseus.user.User;
import ca.qc.ircm.prohits2perseus.user.UserRepository;
import java.security.AccessControlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for authorization.
 */
@Service
@Transactional
public class AuthorizationService {
  private UserRepository userRepository;

  @Autowired
  public AuthorizationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private User currentUser() {
    String username = System.getProperty("user.name");
    return userRepository.findByUsername(username).orElse(null);
  }

  /**
   * Validates that user has access to data.
   *
   * @param data
   *          data
   */
  public void checkRead(ProjectData data) {
    if (data == null || data.getProject() == null) {
      return;
    }

    User user = currentUser();
    Project project = data.getProject();
    if (user.getProjects().stream().filter(pr -> pr.getId().equals(project.getId())).findAny()
        .isEmpty()) {
      throw new AccessControlException("user " + user + " cannot access data " + data);
    }
  }
}
