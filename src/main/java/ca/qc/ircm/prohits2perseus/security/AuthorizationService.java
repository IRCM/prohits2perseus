/*
 * Copyright (c) 2020 Institut de recherches cliniques de Montreal (IRCM)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ca.qc.ircm.prohits2perseus.security;

import ca.qc.ircm.prohits2perseus.project.Project;
import ca.qc.ircm.prohits2perseus.project.ProjectData;
import ca.qc.ircm.prohits2perseus.user.User;
import ca.qc.ircm.prohits2perseus.user.UserRepository;
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
    if (user == null) {
      throw new IllegalStateException("unkown user cannot access data " + data);
    }
    Project project = data.getProject();
    if (user.getProjects().stream().filter(pr -> pr.getId().equals(project.getId())).findAny()
        .isEmpty()) {
      throw new IllegalStateException("user " + user + " cannot access data " + data);
    }
  }
}
