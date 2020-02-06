package ca.qc.ircm.prohits2perseus.sample;

import ca.qc.ircm.processing.GeneratePropertyNames;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Sample.
 */
@Entity
@Table(name = Sample.TABLE_NAME)
@GeneratePropertyNames
public class Sample implements Serializable {
  public static final String TABLE_NAME = "band";
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

  public Bait getBait() {
    return bait;
  }

  public void setBait(Bait bait) {
    this.bait = bait;
  }
}
