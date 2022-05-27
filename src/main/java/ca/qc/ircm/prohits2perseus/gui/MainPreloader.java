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

import ca.qc.ircm.prohits2perseus.AppResources;
import ca.qc.ircm.prohits2perseus.ApplicationStarted;
import ca.qc.ircm.prohits2perseus.Main;
import java.net.URL;
import java.util.Locale;
import javafx.application.Preloader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JavaFX application preloader.
 */
public class MainPreloader extends Preloader {
  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  /**
   * Preloader's stage.
   */
  private Stage stage;
  /**
   * Main container of this view.
   */
  private HBox view = new HBox();
  /**
   * Message to show.
   */
  private Label label = new Label();

  /**
   * Starts and shows preloader dialog.
   * 
   * @param stage
   *          preloader's stage
   * @throws Exception
   *           could not create preloader dialog
   */
  @Override
  public void start(Stage stage) throws Exception {
    this.stage = stage;
    stage.initStyle(StageStyle.UNDECORATED);
    URL stylesheet = getClass().getResource("/" + getClass().getSimpleName() + ".css");
    AppResources baseResources = new AppResources("", Locale.getDefault());
    AppResources resources = new AppResources(getClass(), Locale.getDefault());
    view.getStylesheets().add(stylesheet.toExternalForm());
    view.getStyleClass().add("preloader");
    view.setCursor(Cursor.WAIT);
    view.getChildren().add(label);
    label.setText(resources.message("message", baseResources.message("name")));
    Scene scene = new Scene(view);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Hides preloader dialog.
   * 
   * @param info
   *          notification that app started properly
   */
  @Override
  public void handleApplicationNotification(PreloaderNotification info) {
    logger.warn("ApplicationStarted");
    if (info instanceof ApplicationStarted) {
      stage.hide();
    }
  }

  /**
   * Show error message about why the app could not start.
   * 
   * @param info
   *          information about the error that prevented app from starting normally
   *
   * @return always true since an error message is always shown to user
   */
  @Override
  public boolean handleErrorNotification(ErrorNotification info) {
    logger.error("Could not start application", info.getCause());
    AppResources baseResources = new AppResources("", Locale.getDefault());
    AppResources resources = new AppResources(getClass(), Locale.getDefault());
    stage.hide();
    new Alert(Alert.AlertType.ERROR, resources.message("error", baseResources.message("name")))
        .showAndWait();
    return true;
  }
}
