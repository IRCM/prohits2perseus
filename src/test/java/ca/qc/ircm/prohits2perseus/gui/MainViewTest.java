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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testfx.framework.junit.ApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
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
  @Ignore("Does not work on Mojave right now")
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
