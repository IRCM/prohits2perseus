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
  /**
   * Name of database table containing baits.
   */
  public static final String TABLE_NAME = "Bait";
  /**
   * Serialization version number.
   */
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
   * Returns bait's name.
   * 
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets bait's name.
   * 
   * @param name
   *          name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns bait's project.
   * 
   * @return project
   */
  @Override
  public Project getProject() {
    return project;
  }

  /**
   * Sets bait's project
   * 
   * @param project
   *          project
   */
  public void setProject(Project project) {
    this.project = project;
  }

  /**
   * Returns bait's gene identifier.
   * 
   * @return gene identifier
   */
  public Long getGeneId() {
    return geneId;
  }

  /**
   * Sets bait's gene identifier.
   * 
   * @param geneId
   *          gene identifier
   */
  public void setGeneId(Long geneId) {
    this.geneId = geneId;
  }
}
