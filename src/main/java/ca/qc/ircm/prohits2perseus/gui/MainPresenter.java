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

import static ca.qc.ircm.prohits2perseus.sample.SampleProperties.CONTROL;
import static ca.qc.ircm.prohits2perseus.sample.SampleProperties.PERSEUS;

import ca.qc.ircm.javafx.JavafxUtils;
import ca.qc.ircm.prohits2perseus.AppResources;
import ca.qc.ircm.prohits2perseus.bait.Bait;
import ca.qc.ircm.prohits2perseus.prohits.ConvertTask;
import ca.qc.ircm.prohits2perseus.prohits.ConvertTaskFactory;
import ca.qc.ircm.prohits2perseus.prohits.FetchSamplesTask;
import ca.qc.ircm.prohits2perseus.prohits.FetchSamplesTaskFactory;
import ca.qc.ircm.prohits2perseus.prohits.NormalizeMetadata;
import ca.qc.ircm.prohits2perseus.prohits.ParseSampleCompareTask;
import ca.qc.ircm.prohits2perseus.prohits.ParseSampleCompareTaskFactory;
import ca.qc.ircm.prohits2perseus.prohits.SampleCompareMetadata;
import ca.qc.ircm.prohits2perseus.sample.Sample;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.converter.DefaultStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Main view presenter.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainPresenter {
  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(MainPresenter.class);
  /**
   * Main container of this view.
   */
  @FXML
  private BorderPane view;
  /**
   * Location of a Prohits sample comparison file.
   */
  @FXML
  private Label file;
  /**
   * Table containing samples parsed from Prohits sample comparison file.
   */
  @FXML
  private TableView<Sample> samples;
  /**
   * Sample id column.
   */
  @FXML
  private TableColumn<Sample, Long> sampleId;
  /**
   * Sample name column.
   */
  @FXML
  private TableColumn<Sample, String> sampleName;
  /**
   * Bait column.
   */
  @FXML
  private TableColumn<Sample, String> sampleBait;
  /**
   * If sample is a control column.
   */
  @FXML
  private TableColumn<Sample, Boolean> sampleControl;
  /**
   * Sample name that will be used for Perseus.
   */
  @FXML
  private TableColumn<Sample, String> samplePerseus;
  /**
   * Checkbox to select if data should be normalized.
   */
  @FXML
  private CheckBox normalization;
  /**
   * Pane containing gene selection.
   */
  @FXML
  private Pane genePane;
  /**
   * Gene on which to normalize data.
   */
  @FXML
  private TextField gene;
  /**
   * Show an error if gene cannot be found in Prohits sample comparison file.
   */
  @FXML
  private Label geneError;
  /**
   * Prohits sample comparison file.
   */
  private ObjectProperty<File> fileProperty = new SimpleObjectProperty<>();
  /**
   * Allows to select files.
   */
  private FileChooser fileChooser = new FileChooser();
  /**
   * Samples present in Prohits sample comparison file.
   */
  private SampleCompareMetadata metadata;
  /**
   * Factory to create {@link ParseSampleCompareTask}.
   */
  private final ParseSampleCompareTaskFactory parseFactory;
  /**
   * Factory to create {@link FetchSamplesTask}.
   */
  private final FetchSamplesTaskFactory fetchSamplesFactory;
  /**
   * Factory to create {@link ConvertTask}.
   */
  private final ConvertTaskFactory convertTaskFactory;

  /**
   * Creates an instance of MainPresenter.
   * 
   * @param parseFactory
   *          factory to create {@link ParseSampleCompareTask}
   * @param fetchSamplesFactory
   *          factory to create {@link FetchSamplesTask}
   * @param convertTaskFactory
   *          factory to create {@link ConvertTask}
   */
  @Autowired
  protected MainPresenter(ParseSampleCompareTaskFactory parseFactory,
      FetchSamplesTaskFactory fetchSamplesFactory, ConvertTaskFactory convertTaskFactory) {
    this.parseFactory = parseFactory;
    this.fetchSamplesFactory = fetchSamplesFactory;
    this.convertTaskFactory = convertTaskFactory;
  }

  /**
   * Initializes presenter.
   */
  @FXML
  private void initialize() {
    samples.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    samples.setEditable(true);
    sampleId.setCellValueFactory(sample -> new SimpleObjectProperty<>(
        sample.getValue() != null ? sample.getValue().getId() : null));
    sampleName.setCellValueFactory(sample -> new SimpleStringProperty(
        sample.getValue() != null ? sample.getValue().getName() : null));
    sampleBait.setCellValueFactory(sample -> new SimpleStringProperty(
        sample.getValue() != null ? sample.getValue().getBait().getName() : null));
    sampleControl.setCellFactory(column -> new CheckBoxTableCell<>());
    sampleControl.setCellValueFactory(new PropertyValueFactory<>(CONTROL));
    samplePerseus.getStyleClass().add(PERSEUS);
    samplePerseus.setCellFactory(column -> new TextFieldTableCell<>(new DefaultStringConverter()) {
      @Override
      public void commitEdit(String newValue) {
        super.commitEdit(newValue);
        validateSamples();
      }
    });
    samplePerseus.setCellValueFactory(new PropertyValueFactory<>(PERSEUS));
    gene.disableProperty().bind(normalization.selectedProperty().not());
    gene.textProperty().addListener(e -> validateGene());
    geneError.setVisible(false);
  }

  /**
   * Open file selection dialog to select Prohits sample comparison file.
   * 
   * @param event
   *          event
   */
  @FXML
  private void browse(ActionEvent event) {
    JavafxUtils.setValidInitialDirectory(fileChooser);
    File selection = fileChooser.showOpenDialog(view.getScene().getWindow());
    if (selection != null) {
      fileChooser.setInitialDirectory(selection.getParentFile());
      fileProperty.set(selection);
      file.setText(selection.getName());
      file.setTooltip(new Tooltip(selection.getPath()));
      Platform.runLater(() -> parseFile(selection));
    }
  }

  /**
   * Parses samples present Prohits sample comparison file.
   *
   * <p>
   * The parsing is done in a task since it could take some time to parse the file. A progress
   * dialog is shown in the meantime.
   * </p>
   * 
   * @param file
   *          Prohits sample comparison file
   */
  private void parseFile(File file) {
    logger.debug("parsing file {}", file);
    final AppResources resources = new AppResources(MainView.class, Locale.getDefault());
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
    task.setOnSucceeded(e -> {
      metadata = task.getValue();
      Platform.runLater(() -> fetchSamples(metadata, file));
    });
    new Thread(task).start();
  }

  /**
   * Fetches samples from the database. Samples fetched are those present in Prohits sample
   * comparison file.
   *
   * <p>
   * Since fetching samples from the database can take some time, it is done in a task. A progress
   * dialog is shown in the meantime.
   * </p>
   * 
   * @param metadata
   *          metadata from parsed Prohits sample comparison file
   * @param file
   *          Prohits sample comparison file
   */
  private void fetchSamples(SampleCompareMetadata metadata, File file) {
    logger.debug("fetching samples for file {}", file);
    final AppResources resources = new AppResources(MainView.class, Locale.getDefault());
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
      for (int i = 0; i < samples.size(); i++) {
        if (samples.get(i) == null) {
          Sample sample = new Sample();
          sample.setName(metadata.samples.get(i));
          sample.setBait(new Bait());
          samples.set(i, sample);
        }
        Sample sample = samples.get(i);
        if (sample.getBait().getName() == null) {
          sample.getBait().setName("unknown");
        }
      }
      samples.stream().forEach(sample -> sample.setPerseus(perseus(sample, samples)));
      this.samples.setItems(FXCollections.observableArrayList(samples));
    });
    new Thread(task).start();
  }

  /**
   * Returns sample's name to use in Perseus.
   *
   * <p>
   * Sample's name is based on the bait. If multiple samples have the same bait, an underscore and a
   * number is appended to bait's name.
   * </p>
   * 
   * @param sample
   *          sample
   * @param samples
   *          all samples to resolve conflicts if many samples have the same bait
   * @return sample's name to use in Perseus
   */
  private String perseus(Sample sample, List<Sample> samples) {
    NumberFormat format = new DecimalFormat("00");
    String bait = sample.getBait().getName();
    List<Sample> conflicts =
        samples.stream().filter(sa -> sa != sample && sa.getBait().getName().equalsIgnoreCase(bait))
            .collect(Collectors.toList());
    long lowerConflicts = conflicts.stream().filter(sa -> sa.getId() < sample.getId()).count();
    return bait + "_" + format.format(lowerConflicts + 1);
  }

  /**
   * Validates that all samples have a different name for Perseus.
   * 
   * @return true if all samples pass validation, false otherwise
   */
  @SuppressWarnings("unchecked")
  private boolean validateSamples() {
    List<TableCell<Sample, String>> cells = samples.lookupAll("." + PERSEUS + ".table-cell")
        .stream().map(cell -> (TableCell<Sample, String>) cell).filter(cell -> !cell.isEmpty())
        .collect(Collectors.toList());
    cells.forEach(cell -> cell.getStyleClass().remove("error"));
    Map<String, Integer> perseusCount = new HashMap<>();
    cells.stream().map(cell -> cell.getItem()).forEach(perseus -> {
      perseusCount.putIfAbsent(perseus, 0);
      perseusCount.put(perseus, perseusCount.get(perseus) + 1);
    });
    cells.stream().filter(cell -> perseusCount.get(cell.getItem()) > 1)
        .forEach(cell -> cell.getStyleClass().add("error"));
    return perseusCount.values().stream().filter(value -> value > 1).findAny().isPresent();
  }

  /**
   * Validate that selected gene is in Prohits sample comparison file.
   * 
   * @return true if gene is valid, false otherwise
   */
  private boolean validateGene() {
    genePane.getStyleClass().remove("error");
    geneError.setVisible(false);
    if (metadata != null) {
      String gene = this.gene.getText();
      if (!metadata.genes.contains(gene)) {
        genePane.getStyleClass().add("error");
        geneError.setVisible(true);
        return false;
      }
    }
    return true;
  }

  /**
   * Converts Prohits sample comparison file into a format supported by Perseus.
   *
   * <p>
   * Since the conversion can take some time, it is done in a task. A progress dialog is shown in
   * the meantime.
   * </p>
   * <p>
   * TODO Conversion should not be launched if validation fails.
   * </p>
   *
   * @param event
   *          event
   */
  @FXML
  private void convert(ActionEvent event) {
    logger.debug("converting file {} to Perseus", file);
    final AppResources resources = new AppResources(MainView.class, Locale.getDefault());
    File file = fileProperty.get();
    boolean normalize = normalization.isSelected();
    NormalizeMetadata normalizeMetadata = new NormalizeMetadata();
    normalizeMetadata.geneName = gene.getText();
    normalizeMetadata.ignoreSamples =
        samples.getItems().stream().filter(sample -> sampleControl.getCellData(sample))
            .map(sample -> sample.getPerseus()).collect(Collectors.toList());
    final Window window = view.getScene().getWindow();
    final ConvertTask task = convertTaskFactory.create(file, samples.getItems(), normalize,
        normalizeMetadata, Locale.getDefault());
    final ProgressDialog progress = new ProgressDialog(window, task);
    task.stateProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == State.FAILED || newValue == State.SUCCEEDED || newValue == State.CANCELLED) {
        progress.close();
      }
    });
    task.setOnFailed(e -> {
      logger.warn("error when converting file {} to Perseus", file, task.getException());
      new Alert(Alert.AlertType.ERROR,
          resources.message("conversionError", file.getName(), task.getException().getMessage()))
              .showAndWait();
    });
    task.setOnSucceeded(e -> {
      @SuppressWarnings("unchecked")
      List<File> outputs = (List<File>) e.getSource().getValue();
      logger.warn("converted file {} to Perseus succesfully", file, task.getException());
      Object[] replacements = new Object[outputs.size() + 2];
      replacements[0] = file.getName();
      replacements[1] = outputs.size();
      for (int i = 0; i < outputs.size(); i++) {
        replacements[i + 2] = outputs.get(i).getName();
      }
      new Alert(Alert.AlertType.CONFIRMATION, resources.message("conversion", replacements))
          .showAndWait();
    });
    new Thread(task).start();
  }

  /**
   * Show {@link AboutDialog}.
   * 
   * @param event
   *          event
   */
  @FXML
  private void about(ActionEvent event) {
    new AboutDialog(view.getScene().getWindow());
  }
}
