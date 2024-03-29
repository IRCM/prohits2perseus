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

package ca.qc.ircm.prohits2perseus;

import ca.qc.ircm.javafx.JavafxUtils;
import ca.qc.ircm.prohits2perseus.gui.MainView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application.
 */
@SpringBootApplication
public class MainApplication extends AbstractSpringBootJavafxApplication {
  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

  /**
   * Initializes the main application.
   * 
   * @throws Exception
   *           Cannot initialize the main application
   */
  @Override
  public void init() throws Exception {
    super.init();
    logger.debug("database url: {}",
        applicationContext.getEnvironment().getProperty("spring.datasource.url"));
    com.airhacks.afterburner.injection.Injector
        .setInstanceSupplier(clazz -> applicationContext.getBean(clazz));
  }

  /**
   * Shows application's main windows.
   *
   * @param stage
   *          the primary stage for this application, onto which the application scene can be set.
   *          Applications may create other stages, if needed, but they will not be primary stages.
   * @throws Exception
   *           Could not show the main window
   */
  @Override
  public void start(Stage stage) throws Exception {
    MainView view = new MainView();
    Pane root = (Pane) view.getView();
    root.setPrefHeight(600);
    root.setPrefWidth(800);
    stage.setTitle(view.getResourceBundle().getString("title"));
    Scene scene = new Scene(view.getView());
    scene.getStylesheets().add("application.css");
    stage.setScene(scene);
    JavafxUtils.setMaxSizeForScreen(stage);
    notifyPreloader(new ApplicationStarted());
    stage.show();
  }
}
