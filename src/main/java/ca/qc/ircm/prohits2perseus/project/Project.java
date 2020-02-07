package ca.qc.ircm.prohits2perseus.project;

import ca.qc.ircm.processing.GeneratePropertyNames;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Project.
 */
@Entity
@Table(name = Project.TABLE_NAME)
@GeneratePropertyNames
public class Project {
  public static final String TABLE_NAME = "Projects";

  /**
   * Id.
   */
  @Id
  @Column(unique = true, nullable = false)
  private Long id;
  /**
   * Name.
   */
  @Column
  private String name;

  @Override
  public String toString() {
    return "Project [id=" + id + ", name=" + name + "]";
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
