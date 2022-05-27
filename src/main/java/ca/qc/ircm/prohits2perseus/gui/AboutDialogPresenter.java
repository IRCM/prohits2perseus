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

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * About dialog presenter.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AboutDialogPresenter {
  /**
   * About message.
   */
  @FXML
  private Label message;
  /**
   * Ok button.
   */
  @FXML
  private Button ok;
  /**
   * Resource bundle for messages.
   */
  @FXML
  private ResourceBundle resources;
  /**
   * Version of app.
   */
  @Value("${version:unkown version}")
  private String version;

  /**
   * Initializes presenter.
   */
  @FXML
  private void initialize() {
    message.setText(MessageFormat.format(resources.getString("message"), version));
    ok.requestFocus();
  }

  /**
   * Closes dialog.
   * 
   * @param event
   *          close event
   */
  @FXML
  private void close(Event event) {
    ok.getScene().getWindow().hide();
  }

  /**
   * Brings focus on default button.
   */
  void focusOnDefault() {
    ok.requestFocus();
  }
}
