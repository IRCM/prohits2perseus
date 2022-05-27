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

package ca.qc.ircm.prohits2perseus.user;

import ca.qc.ircm.processing.GeneratePropertyNames;
import ca.qc.ircm.prohits2perseus.project.Project;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * User.
 */
@Entity
@Table(name = User.TABLE_NAME)
@GeneratePropertyNames
public class User {
  /**
   * Name of database table containing users.
   */
  public static final String TABLE_NAME = "User";

  /**
   * Database identifier.
   */
  @Id
  @Column(unique = true, nullable = false)
  private Long id;
  /**
   * Username.
   */
  @Column
  private String username;
  /**
   * Projects accessible to user.
   */
  @ManyToMany
  @JoinTable(
      name = "ProPermission",
      joinColumns = @JoinColumn(name = "userid"),
      inverseJoinColumns = @JoinColumn(name = "projectid"))
  private List<Project> projects;

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + "]";
  }

  /**
   * Returns database identifier.
   *
   * @return database identifier
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets database identifier.
   *
   * @param id
   *          database identifier
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns username.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets username.
   *
   * @param username
   *          username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns projects accessible by user.
   *
   * @return projects accessible by user
   */
  public List<Project> getProjects() {
    return projects;
  }

  /**
   * Sets projects accessible by user.
   *
   * @param projects
   *          projects accessible by user
   */
  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }
}
