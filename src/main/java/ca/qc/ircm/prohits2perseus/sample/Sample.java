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

package ca.qc.ircm.prohits2perseus.sample;

import ca.qc.ircm.processing.GeneratePropertyNames;
import ca.qc.ircm.prohits2perseus.bait.Bait;
import ca.qc.ircm.prohits2perseus.project.Project;
import ca.qc.ircm.prohits2perseus.project.ProjectData;
import java.io.Serializable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Sample.
 */
@Entity
@Table(name = Sample.TABLE_NAME)
@GeneratePropertyNames
public class Sample implements Serializable, ProjectData {
  /**
   * Name of database table containing samples.
   */
  public static final String TABLE_NAME = "Band";
  /**
   * Serialization version number.
   */
  private static final long serialVersionUID = 1723224097702204200L;

  /**
   * Database identifier.
   */
  @Id
  @Column(unique = true, nullable = false)
  private Long id;
  /**
   * Name.
   */
  @Column(name = "location")
  private String name;
  /**
   * Bait of this sample.
   */
  @ManyToOne
  @JoinColumn(name = "baitid")
  private Bait bait;
  /**
   * Project of this sample.
   */
  @ManyToOne
  @JoinColumn(name = "projectid")
  private Project project;
  /**
   * Sample is a control.
   */
  @Transient
  private BooleanProperty control = new SimpleBooleanProperty();
  /**
   * Name in Perseus.
   */
  @Transient
  private StringProperty perseus = new SimpleStringProperty();

  @Override
  public String toString() {
    return "Sample [id=" + id + ", name=" + name + "]";
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
   * Returns sample's name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets sample's name.
   *
   * @param name
   *          name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns sample's bait.
   *
   * @return bait
   */
  public Bait getBait() {
    return bait;
  }

  /**
   * Sets sample's bait.
   *
   * @param bait
   *          bait
   */
  public void setBait(Bait bait) {
    this.bait = bait;
  }

  /**
   * Returns property containing if sample is a control.
   *
   * @return property containing if sample is a control
   */
  public BooleanProperty controlProperty() {
    return control;
  }

  /**
   * Returns true if sample is a control, false otherwise.
   *
   * @return true if sample is a control, false otherwise
   */
  public boolean isControl() {
    return control.get();
  }

  /**
   * Sets if sample is a control.
   *
   * @param control
   *          control
   */
  public void setControl(boolean control) {
    this.control.set(control);
  }

  /**
   * Returns property containing sample's name to use in Perseus.
   *
   * @return property containing sample's name to use in Perseus
   */
  public StringProperty perseusProperty() {
    return perseus;
  }

  /**
   * Returns sample's name to use in Perseus.
   *
   * @return name
   */
  public String getPerseus() {
    return perseus.get();
  }

  /**
   * Sets sample's name to use in Perseus.
   *
   * @param perseus
   *          name
   */
  public void setPerseus(String perseus) {
    this.perseus.set(perseus);
  }

  /**
   * Returns sample's project.
   * 
   * @return project
   */
  @Override
  public Project getProject() {
    return project;
  }

  /**
   * Sets sample's project.
   *
   * @param project
   *          project
   */
  public void setProject(Project project) {
    this.project = project;
  }
}
