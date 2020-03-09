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

package ca.qc.ircm.prohits2perseus.prohits;

import ca.qc.ircm.prohits2perseus.AppResources;
import ca.qc.ircm.prohits2perseus.sample.Sample;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
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
    try (Writer writer = Files.newBufferedWriter(output, StandardOpenOption.CREATE)) {
      for (List<String> line : content) {
        writer.write(line.stream().collect(Collectors.joining("\t")) + "\n");
      }
    }
  }
}
