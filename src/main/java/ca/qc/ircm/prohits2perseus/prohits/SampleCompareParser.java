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

import static java.util.stream.Collectors.toCollection;

import com.opencsv.CSVParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 * Parses sample comparison file.
 */
@Component
public class SampleCompareParser {
  private static final String GENE_ID_COLUMN = "Gene ID";
  private static final String GENE_NAME_COLUMN = "Gene Name";

  /**
   * Parses sample comparison file metadata.
   *
   * @param file
   *          sample comparison file
   * @return metadata
   * @throws IOException
   *           could not read file
   */
  public SampleCompareMetadata parseMetadata(File file) throws IOException {
    List<String> rawlines = Files.readAllLines(file.toPath());
    List<String[]> lines = new ArrayList<>();
    for (int i = 0; i < rawlines.size(); i++) {
      lines.add(new CSVParser().parseLine(rawlines.get(i)));
    }
    int headerIndex = IntStream.range(0, rawlines.size())
        .filter(i -> lines.get(i)[0].equals(GENE_ID_COLUMN)).findFirst().orElseThrow(
            () -> new IOException("File " + file + " not a Prohits sample comparison file"));
    int geneNameIndex = Arrays.asList(lines.get(headerIndex)).indexOf(GENE_NAME_COLUMN);
    String[] sampleColumns = lines.get(headerIndex - 2);
    SampleCompareMetadata metadata = new SampleCompareMetadata();
    metadata.headerLineNumber = headerIndex;
    metadata.geneNameIndex = geneNameIndex;
    metadata.samplesStartIndex = IntStream.range(0, sampleColumns.length)
        .filter(i -> !sampleColumns[i].isEmpty()).findFirst().orElse(-1);
    metadata.samples =
        Stream.of(sampleColumns).filter(sample -> !sample.isEmpty()).collect(Collectors.toList());
    metadata.genes = lines.stream().skip(headerIndex + 1).map(columns -> columns[geneNameIndex])
        .collect(Collectors.toList());
    return metadata;
  }

  /**
   * Parses sample comparison file, skipping lines before header and using sample names as headers.
   *
   * @param file
   *          sample comparison file
   * @return all lines beginning with header line
   * @throws IOException
   *           could not parse file
   */
  public List<List<String>> parse(File file) throws IOException {
    SampleCompareMetadata metadata = parseMetadata(file);
    List<String> rawlines = Files.readAllLines(file.toPath());
    List<List<String>> lines = new ArrayList<>();
    for (int i = 0; i < rawlines.size(); i++) {
      lines.add(Stream.of(new CSVParser().parseLine(rawlines.get(i)))
          .collect(toCollection(ArrayList::new)));
    }
    List<String> header = lines.get(metadata.headerLineNumber);
    for (int i = 0; i < metadata.samples.size(); i++) {
      header.set(i + metadata.samplesStartIndex, metadata.samples.get(i));
    }
    return lines.stream().skip(metadata.headerLineNumber)
        .collect(Collectors.toCollection(ArrayList::new));
  }
}
