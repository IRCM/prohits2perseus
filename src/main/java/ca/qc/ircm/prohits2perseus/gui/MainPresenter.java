package ca.qc.ircm.prohits2perseus.gui;

import ca.qc.ircm.javafx.JavafxUtils;
import ca.qc.ircm.prohits2perseus.sample.Sample;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Main view presenter.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainPresenter {
  @FXML
  private BorderPane view;
  @FXML
  private Pane filePane;
  @FXML
  private Label file;
  @FXML
  private Pane baitPane;
  @FXML
  private TextField bait;
  @FXML
  private TableView<Sample> samples;
  @FXML
  private TableColumn<Sample, Long> sampleId;
  @FXML
  private TableColumn<Sample, String> sampleName;
  @FXML
  private TableColumn<Sample, String> sampleBait;
  @FXML
  private TableColumn<Sample, String> samplePerseus;
  private FileChooser fileChooser = new FileChooser();

  @FXML
  private void browse(ActionEvent event) {
    JavafxUtils.setValidInitialDirectory(fileChooser);
    File selection = fileChooser.showOpenDialog(view.getScene().getWindow());
    if (selection != null) {
      fileChooser.setInitialDirectory(selection.getParentFile());
      file.setText(selection.getName());
      file.setTooltip(new Tooltip(selection.getPath()));
    }
  }

  @FXML
  private void convert(ActionEvent event) {

  }
}
