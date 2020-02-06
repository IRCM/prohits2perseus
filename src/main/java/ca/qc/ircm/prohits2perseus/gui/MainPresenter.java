package ca.qc.ircm.prohits2perseus.gui;

import ca.qc.ircm.javafx.JavafxUtils;
import ca.qc.ircm.prohits2perseus.AppResources;
import ca.qc.ircm.prohits2perseus.prohits.FetchSamplesTask;
import ca.qc.ircm.prohits2perseus.prohits.FetchSamplesTaskFactory;
import ca.qc.ircm.prohits2perseus.prohits.ParseSampleCompareTask;
import ca.qc.ircm.prohits2perseus.prohits.ParseSampleCompareTaskFactory;
import ca.qc.ircm.prohits2perseus.prohits.SampleCompareMetadata;
import ca.qc.ircm.prohits2perseus.sample.Sample;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Main view presenter.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainPresenter {
  private static final Logger logger = LoggerFactory.getLogger(MainPresenter.class);
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
  private final ParseSampleCompareTaskFactory parseFactory;
  private final FetchSamplesTaskFactory fetchSamplesFactory;

  protected MainPresenter(ParseSampleCompareTaskFactory parseFactory,
      FetchSamplesTaskFactory fetchSamplesFactory) {
    this.parseFactory = parseFactory;
    this.fetchSamplesFactory = fetchSamplesFactory;
  }

  @FXML
  private void initialize() {
    samples.setEditable(true);
    sampleId.setCellValueFactory(sample -> new SimpleObjectProperty<>(
        sample.getValue() != null ? sample.getValue().getId() : null));
    sampleName.setCellValueFactory(sample -> new SimpleStringProperty(
        sample.getValue() != null ? sample.getValue().getName() : null));
    sampleBait.setCellValueFactory(sample -> new SimpleStringProperty(
        sample.getValue() != null ? sample.getValue().getBait().getName() : null));
    samplePerseus.setCellFactory(column -> new PerseusCell<>());
    samplePerseus.setCellValueFactory(new PropertyValueFactory<>("perseus"));
  }

  @FXML
  private void browse(ActionEvent event) {
    JavafxUtils.setValidInitialDirectory(fileChooser);
    File selection = fileChooser.showOpenDialog(view.getScene().getWindow());
    if (selection != null) {
      fileChooser.setInitialDirectory(selection.getParentFile());
      file.setText(selection.getName());
      file.setTooltip(new Tooltip(selection.getPath()));
      Platform.runLater(() -> parseFile(selection));
    }
  }

  private void parseFile(File file) {
    logger.debug("parsing file {}", file);
    final AppResources resources = new AppResources(getClass(), Locale.getDefault());
    final Window window = view.getScene().getWindow();
    final ParseSampleCompareTask task = parseFactory.create(file, Locale.getDefault());
    final ProgressDialog progress = new ProgressDialog(window, task);
    task.stateProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == State.FAILED || newValue == State.SUCCEEDED || newValue == State.CANCELLED) {
        progress.close();
      }
    });
    task.setOnFailed(e -> {
      logger.warn("error when parsing file {}", file, task.getException());
      new Alert(Alert.AlertType.ERROR, resources.message("parseError", file.getName()))
          .showAndWait();
    });
    task.setOnSucceeded(e -> Platform.runLater(() -> fetchSamples(task.getValue(), file)));
    new Thread(task).start();
  }

  private void fetchSamples(SampleCompareMetadata metadata, File file) {
    logger.debug("fetching samples for file {}", file);
    final AppResources resources = new AppResources(getClass(), Locale.getDefault());
    final Window window = view.getScene().getWindow();
    final FetchSamplesTask task = fetchSamplesFactory.create(metadata, Locale.getDefault());
    final ProgressDialog progress = new ProgressDialog(window, task);
    task.stateProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == State.FAILED || newValue == State.SUCCEEDED || newValue == State.CANCELLED) {
        progress.close();
      }
    });
    task.setOnFailed(e -> {
      logger.warn("error when fetching samples of file {}", file, task.getException());
      new Alert(Alert.AlertType.ERROR, resources.message("fetchSamplesError", file.getName()))
          .showAndWait();
    });
    task.setOnSucceeded(e -> {
      List<Sample> samples = task.getValue();
      samples.stream().forEach(sample -> sample.setPerseus(perseus(sample, samples)));
      this.samples.setItems(FXCollections.observableArrayList(samples));
    });
    new Thread(task).start();
  }

  private String perseus(Sample sample, List<Sample> samples) {
    NumberFormat format = new DecimalFormat("00");
    String bait = sample.getBait().getName();
    List<Sample> conflicts =
        samples.stream().filter(sa -> sa != sample && sa.getBait().getName().equalsIgnoreCase(bait))
            .collect(Collectors.toList());
    long lowerConflicts = conflicts.stream().filter(sa -> sa.getId() < sample.getId()).count();
    return bait + "_" + format.format(lowerConflicts + 1);
  }

  @FXML
  private void convert(ActionEvent event) {
  }

  private class PerseusCell<S> extends TableCell<S, String> {
    private final TextField textfield = new TextField();

    private PerseusCell() {
      getStyleClass().add("color-cell");
      setGraphic(null);
      textfield.setOnKeyPressed(e -> {
        if (e.getCode() == KeyCode.ENTER) {
          commitEdit(textfield.getText());
        }
      });
    }

    @Override
    protected void updateItem(String value, boolean empty) {
      super.updateItem(value, empty);
      logger.debug("updateItem {}", value);
      if (!empty) {
        setText(value);
        textfield.setText(value);
      } else {
        setText("");
        textfield.setText("");
      }
    }

    @Override
    public void startEdit() {
      super.startEdit();
      setText("");
      setGraphic(textfield);
    }

    @Override
    public void cancelEdit() {
      super.cancelEdit();
      setText(getItem());
      setGraphic(null);
    }

    @Override
    public void commitEdit(String value) {
      super.commitEdit(value);
      setGraphic(null);
    }
  }
}
