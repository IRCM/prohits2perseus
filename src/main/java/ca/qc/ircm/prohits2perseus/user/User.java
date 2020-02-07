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
  public static final String TABLE_NAME = "User";

  /**
   * Id.
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }
}
