package ca.qc.ircm.prohits2perseus.bait;

import ca.qc.ircm.processing.GeneratePropertyNames;
import ca.qc.ircm.prohits2perseus.project.Project;
import ca.qc.ircm.prohits2perseus.project.ProjectData;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Bait.
 */
@Entity
@Table(name = Bait.TABLE_NAME)
@GeneratePropertyNames
public class Bait implements Serializable, ProjectData {
  public static final String TABLE_NAME = "Bait";
  private static final long serialVersionUID = 7069067891007593075L;

  /**
   * Database identifier.
   */
  @Id
  @Column(unique = true, nullable = false)
  private Long id;
  /**
   * Name.
   */
  @Column(name = "genename")
  private String name;
  /**
   * Gene identifier.
   */
  @Column
  private Long geneId;
  /**
   * Project of this bait.
   */
  @ManyToOne
  @JoinColumn(name = "projectid")
  private Project project;

  @Override
  public String toString() {
    return "Bait [id=" + id + ", name=" + name + "]";
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

  @Override
  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Long getGeneId() {
    return geneId;
  }

  public void setGeneId(Long geneId) {
    this.geneId = geneId;
  }
}
