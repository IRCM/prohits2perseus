package ca.qc.ircm.prohits2perseus.prohits;

import ca.qc.ircm.prohits2perseus.AppResources;
import ca.qc.ircm.prohits2perseus.sample.Sample;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts Prohits sample comparison file into files readable by Perseus.
 */
public class ConvertTask extends Task<List<File>> {
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(ConvertTask.class);
  private final File input;
  private final List<Sample> samples;
  private final boolean normalize;
  private final NormalizeMetadata normalizeMetadata;
  private final Locale locale;
  private final SampleCompareParser parser;
  private final PerseusConverter converter;
  private final PerseusNormalizer normalizer;

  protected ConvertTask(File input, List<Sample> samples, boolean normalize,
      NormalizeMetadata normalizeMetadata, Locale locale, SampleCompareParser parser,
      PerseusConverter converter, PerseusNormalizer normalizer) {
    this.input = input;
    this.samples = samples;
    this.normalize = normalize;
    this.normalizeMetadata = normalizeMetadata;
    this.locale = locale;
    this.parser = parser;
    this.converter = converter;
    this.normalizer = normalizer;
  }

  @Override
  protected List<File> call() throws Exception {
    double step = 1.0 / (normalize ? 4 : 3);
    AppResources resources = new AppResources(ConvertTask.class, locale);
    updateTitle(resources.message("title", input.getName()));
    updateMessage(resources.message("parseMetadata"));
    SampleCompareMetadata metadata = parser.parseMetadata(input);
    updateProgress(step, 1.0);
    updateMessage(resources.message("parse"));
    List<List<String>> data = parser.parse(input);
    updateProgress(step * 2, 1.0);
    updateMessage(resources.message("convert"));
    List<List<String>> converted = converter.toPerseus(data, metadata.samplesStartIndex, samples);
    String baseFilename = input.getName().substring(0,
        input.getName().contains(".") ? input.getName().lastIndexOf(".")
            : input.getName().length());
    List<File> outputs = new ArrayList<>();
    Path output = input.toPath().resolveSibling(baseFilename + ".txt");
    writeTabDelimited(output, converted);
    outputs.add(output.toFile());
    updateProgress(step * 3, 1.0);
    if (normalize) {
      updateMessage(resources.message("normalize"));
      normalizeMetadata.samplesStartIndex = metadata.samplesStartIndex;
      normalizeMetadata.geneNameIndex = metadata.geneNameIndex;
      List<List<String>> normalized = normalizer.normalize(converted, normalizeMetadata);
      output = input.toPath().resolveSibling(baseFilename + "-normalized.txt");
      writeTabDelimited(output, normalized);
      outputs.add(output.toFile());
    }
    updateProgress(1.0, 1.0);
    return outputs;
  }

  private void writeTabDelimited(Path output, List<List<String>> content) throws IOException {
    try (ICSVWriter writer =
        new CSVWriterBuilder(Files.newBufferedWriter(output, StandardOpenOption.CREATE))
            .withSeparator('\t').build()) {
      List<String[]> csv =
          content.stream().map(line -> line.toArray(new String[0])).collect(Collectors.toList());
      writer.writeAll(csv);
    }
  }
}
