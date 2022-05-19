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

import ca.qc.ircm.prohits2perseus.test.config.TestFxTestAnnotations;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.testfx.framework.junit.ApplicationTest;

@TestFxTestAnnotations
public class MainViewTest extends ApplicationTest {
  private MainView view;
  private Scene scene;
  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public void start(Stage stage) throws Exception {
    com.airhacks.afterburner.injection.Injector
        .setInstanceSupplier(clazz -> applicationContext.getBean(clazz));
    view = new MainView();
    Pane root = (Pane) view.getView();
    root.setPrefHeight(600);
    root.setPrefWidth(800);
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  @Disabled("Does not work on Mojave right now")
  public void browse() throws Throwable {
    File file = new File(getClass().getResource("/comparison_matrix.csv").toURI());
    clickOn(".button.file");
    applyPathToChooser(file.getPath());
  }

  private void applyPathToChooser(String filePath) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection stringSelection = new StringSelection(filePath);
    clipboard.setContents(stringSelection, stringSelection);
    press(KeyCode.CONTROL).press(KeyCode.V).release(KeyCode.V).release(KeyCode.CONTROL);
    push(KeyCode.ENTER);
  }
}
