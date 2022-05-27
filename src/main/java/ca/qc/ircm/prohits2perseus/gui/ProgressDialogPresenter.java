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

package ca.qc.ircm.prohits2perseus.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Progress dialog controller.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProgressDialogPresenter {
  /**
   * If background task should be cancelled.
   */
  private final BooleanProperty cancelledProperty = new SimpleBooleanProperty();
  /**
   * Progress bar.
   */
  @FXML
  ProgressBar progressBar;
  /**
   * Progress indicator.
   */
  @FXML
  ProgressIndicator progressIndicator;
  /**
   * Progression message.
   */
  @FXML
  Label message;
  /**
   * Cancel button.
   */
  @FXML
  Button cancel;

  /**
   * Initializes progress dialog.
   */
  @FXML
  private void initialize() {
    progressIndicator.progressProperty().bind(progressBar.progressProperty());

    cancel.requestFocus();
  }

  /**
   * Returns progress property.
   *
   * <p>
   * This value is usually between 0.0 and 1.0, where 1.0 indicates that the task completed.
   * </p>
   * 
   * @return progress property
   */
  public DoubleProperty progressProperty() {
    return progressBar.progressProperty();
  }

  /**
   * Returns message property.
   *
   * <p>
   * This message is shown to user so he can have an idea of what the program is doing while he
   * waits.
   * </p>
   * 
   * @return message property
   */
  public StringProperty messageProperty() {
    return message.textProperty();
  }

  /**
   * Returns cancelled property.
   *
   * <p>
   * This property becomes true when the user click the cancel button.
   * </p>
   * 
   * @return cancelled property
   */
  public ReadOnlyBooleanProperty cancelledProperty() {
    return cancelledProperty;
  }

  /**
   * Users clicked the cancel button.
   * 
   * @param event
   *          event
   */
  @FXML
  private void cancel(Event event) {
    cancelledProperty.set(true);
  }
}
