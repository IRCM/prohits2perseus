package ca.qc.ircm.prohits2perseus.sample;

import ca.qc.ircm.processing.GeneratePropertyNames;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bait.
 */
@Entity
@Table(name = Bait.TABLE_NAME)
@GeneratePropertyNames
public class Bait implements Serializable {
  public static final String TABLE_NAME = "Bait";
  private static final long serialVersionUID = 7069067891007593075L;

  /**
   * Database identifier.
   */
  @Id
  @Column(unique = true, nullable = false)
  private Long id;
  /**
   * Bait.
   */
  @Column(name = "genename")
  private String name;

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
}
