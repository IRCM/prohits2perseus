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
  @FXML
  private Label message;
  @FXML
  private Button ok;
  @FXML
  private ResourceBundle resources;
  @Value("${version:unkown version}")
  private String version;

  @FXML
  private void initialize() {
    message.setText(MessageFormat.format(resources.getString("message"), version));
    ok.requestFocus();
  }

  @FXML
  private void close(Event event) {
    ok.getScene().getWindow().hide();
  }

  void focusOnDefault() {
    ok.requestFocus();
  }
}
