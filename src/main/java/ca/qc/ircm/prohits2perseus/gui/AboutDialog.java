package ca.qc.ircm.prohits2perseus.gui;

import ca.qc.ircm.javafx.JavafxUtils;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * About dialog.
 */
public class AboutDialog {
  private Stage stage;
  private AboutDialogPresenter presenter;

  /**
   * Shows about dialog.
   *
   * @param owner
   *          dialog's owner
   */
  public AboutDialog(Window owner) {
    stage = new Stage();
    stage.initOwner(owner);
    stage.initModality(Modality.NONE);

    AboutDialogView view = new AboutDialogView();
    presenter = (AboutDialogPresenter) view.getPresenter();
    ResourceBundle resources = view.getResourceBundle();
    Parent root = view.getView();
    JavafxUtils.setMaxSizeForScreen(stage);

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle(resources.getString("title"));
    scene.getStylesheets().add("application.css");

    presenter.focusOnDefault();
    stage.show();
  }

  public void show() {
    stage.show();
  }

  public void hide() {
    stage.hide();
  }
}
