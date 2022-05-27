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
  /**
   * Name of database table containing projects.
   */
  public static final String TABLE_NAME = "Projects";

  /**
   * Database identifier.
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
   * Returns project's name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets project's name.
   *
   * @param name
   *          name
   */
  public void setName(String name) {
    this.name = name;
  }
}
