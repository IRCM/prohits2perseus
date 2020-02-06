package ca.qc.ircm.prohits2perseus.sample;

import ca.qc.ircm.processing.GeneratePropertyNames;
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
public class Sample implements Serializable {
  public static final String TABLE_NAME = "Band";
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

  public BooleanProperty controlProperty() {
    return control;
  }

  public boolean getControl() {
    return control.get();
  }

  public void setControl(boolean control) {
    this.control.set(control);
  }

  public StringProperty perseusProperty() {
    return perseus;
  }

  public String getPerseus() {
    return perseus.get();
  }

  public void setPerseus(String perseus) {
    this.perseus.set(perseus);
  }
}
